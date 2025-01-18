import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ConsumoApi {

    public double getExchangeRate(String baseCurrency, String monedadestino) throws Exception {
        // Dirección del endpoint
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/defba65b23c418df7221286c/latest/" + baseCurrency);

        // Crear el cliente HTTP
        HttpClient client = HttpClient.newHttpClient();

        // Crear la solicitud HTTP
        HttpRequest request = HttpRequest.newBuilder()
                .uri(direccion) // Usa la URI construida
                .build();

        try {
            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar el código de estado HTTP
            if (response.statusCode() != 200) {
                throw new RuntimeException("Error en la solicitud: Código HTTP " + response.statusCode());
            }

            // Convertir la respuesta JSON al objeto Moneda
            Moneda moneda = new Gson().fromJson(response.body(), Moneda.class);

            // Obtener las tasas de conversión
            Map<String, Double> conversionRates = moneda.getConversion_rates();

            // Verificar si la moneda destino está en el mapa
            if (conversionRates.containsKey(monedadestino)) {
                return conversionRates.get(monedadestino);
            } else {
                throw new RuntimeException("Moneda destino no encontrada: " + monedadestino);
            }
        } catch (Exception e) {
            // Capturar y propagar el error con más contexto
            throw new RuntimeException("Error al obtener la tasa de conversión: " + e.getMessage(), e);
        }
    }
}

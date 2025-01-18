import java.text.DecimalFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ConsumoApi consumoApi = new ConsumoApi();

        try {
            while (true) {
                String menu = """
*********************************************
  Sea bienvenido/a al Conversor de Moneda =]
*********************************************

1) Dólar => Peso Chileno
2) Peso Chileno => Dólar
3) Peso Chileno => Peso Argentino
4) Peso Argentino => Dólar
5) Dólar => Peso Argentino
6) Peso colombiano => Dólar
7) Salir

Elija una opción válida:
*********************************************
""" ;

                System.out.println(menu);

                String opcionStr = scanner.nextLine();
                int opcion;

                try {
                    opcion = Integer.parseInt(opcionStr); // Convertir a entero
                } catch (NumberFormatException e) {
                    System.out.println("Opción no válida. Intente nuevamente.");
                    continue;
                }

                if (opcion == 7) {
                    System.out.println("Gracias por usar el conversor de monedas. ¡Hasta pronto!");
                    break;
                }

                System.out.print("Ingrese la cantidad a convertir: ");
                String valorStr = scanner.nextLine();
                double valorAconvertir;

                try {
                    valorStr = valorStr.replace(",", "");
                    valorAconvertir = Double.parseDouble(valorStr);
                } catch (NumberFormatException e) {
                    System.out.println("Cantidad no válida. Intente nuevamente.");
                    continue;
                }

                String monedaBase = "";
                String monedaDestino = "";

                switch (opcion) {
                    case 1:
                        monedaBase = "USD";
                        monedaDestino = "CLP";
                        break;
                    case 2:
                        monedaBase = "CLP";
                        monedaDestino = "USD";
                        break;
                    case 3:
                        monedaBase = "CLP";
                        monedaDestino = "ARS";
                        break;
                    case 4:
                        monedaBase = "ARS";
                        monedaDestino = "USD";
                        break;
                    case 5:
                        monedaBase = "USD";
                        monedaDestino = "ARS";
                        break;
                    case 6:
                        monedaBase = "COP";
                        monedaDestino = "USD";
                        break;
                    default:
                        System.out.println("Opción no válida. Intente nuevamente.");
                        continue;
                }

                double tasaDeConversion = consumoApi.getExchangeRate(monedaBase, monedaDestino);
                double resultadoDeLaConversion = tasaDeConversion * valorAconvertir;

                // Aquí se realiza el redondeo solo para CLP
                if (monedaDestino.equals("CLP")) {
                    resultadoDeLaConversion = Math.round(resultadoDeLaConversion); // Redondear a entero
                }

                // Mostrar la tasa de cambio y el resultado
                System.out.printf("Tasa de cambio: 1 %s = %.2f %s\n", monedaBase, tasaDeConversion, monedaDestino);

                // Formatear el resultado dependiendo si tiene decimales o no
                if (monedaDestino.equals("CLP")) {
                    System.out.printf("Cantidad convertida: %.0f %s\n", resultadoDeLaConversion, monedaDestino); // Mostrar sin decimales
                } else {
                    System.out.printf("Cantidad convertida: %.2f %s\n", resultadoDeLaConversion, monedaDestino); // Mostrar con decimales
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}

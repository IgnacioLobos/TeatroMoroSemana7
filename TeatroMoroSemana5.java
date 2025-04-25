package teatromorosemana5;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class TeatroMoroSemana5 {
    
    public static void mostrarPlanoAsientos(int filas, int asientosPorFila, boolean[]... sector) {
        for (int f = 0; f < filas; f++) {
            System.out.print("Fila " + (f + 1) + ": ");
            for (int a = 0; a < asientosPorFila; a++) {
                if (sector[f][a]) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[O]");
                }
            }
            System.out.println();
        }
    }
    
    
    
    public static boolean seleccionarAsiento(Scanner sc, int filas, int asientosPorFila, boolean[]... sector) {
        System.out.print("\nSeleccione una fila (1-" + filas + ") o 0 para salir: ");
        int filaSeleccionada = sc.nextInt();
        
        if (filaSeleccionada == 0) {
            return false;
        } else if (filaSeleccionada < 1 || filaSeleccionada > filas) {
            System.out.println("Fila inválida.");
            return false;
        }
        
        System.out.print("Seleccione un asiento (1-" + asientosPorFila + "): ");
        int asientoSeleccionado = sc.nextInt();
        
        if (asientoSeleccionado < 1 || asientoSeleccionado > asientosPorFila) {
            System.out.println("Asiento inválido.");
            return false;
        }
        
        boolean[] fila = sector[filaSeleccionada - 1];
        if (fila[asientoSeleccionado - 1]) {
            System.out.println("Ese asiento ya está ocupado.");
            return false;
        } else {
            fila[asientoSeleccionado - 1] = true;
            System.out.println("Compra exitosa: Fila " + filaSeleccionada + " – Asiento " + asientoSeleccionado);
            return true;
        }
    }
    
    static Timer timerReserva = null;
    static boolean reservaPendiente = false;
    static int reservaFila = -1;
    static int reservaAsiento = -1;
    
    public static boolean seleccionarReserva(Scanner sc, int filas, int asientosPorFila, boolean[]... sector) {
        System.out.print("\nSeleccione una fila (1-" + filas + ") o 0 para salir: ");
        int filaSeleccionada = sc.nextInt();
        
        if (filaSeleccionada == 0) {
            return false;
        } else if (filaSeleccionada < 1 || filaSeleccionada > filas) {
            System.out.println("Fila inválida.");
            return false;
        }
        
        System.out.print("Seleccione un asiento (1-" + asientosPorFila + "): ");
        int asientoSeleccionado = sc.nextInt();
        
        if (asientoSeleccionado < 1 || asientoSeleccionado > asientosPorFila) {
            System.out.println("Asiento inválido.");
            return false;
        }
        
        boolean[] fila = sector[filaSeleccionada - 1];
        
        if (fila[asientoSeleccionado - 1]) {
            System.out.println("Ese asiento ya está ocupado.");
            return false;
        } else {
            fila[asientoSeleccionado - 1] = true;
            System.out.println("Reserva exitosa: Fila " + filaSeleccionada + " Asiento " + asientoSeleccionado);
            
            timerReserva = new Timer();
            
            final int filaTemporal = filaSeleccionada;
            final int asientoTemporal = asientoSeleccionado;
            
            TimerTask tareadeCancelacion = new TimerTask() {
                @Override
                
                public void run() {
                    
                    fila[asientoTemporal - 1] = false;
                    System.out.println("!! La Reserva de Fila " + filaTemporal + " Asiento :" + asientoTemporal + " fue Cancelada por tiempo de espera");
                    reservaPendiente = false;
                    reservaFila = -1;
                    reservaAsiento = 1;
                    timerReserva.cancel();
                }
            };
            timerReserva.schedule(tareadeCancelacion, 600000);
            
        }
        return true;
    }
    
    public static double calcularDescuento(double precioBase, int promocion) {
        switch (promocion) {
            case 1 -> {
                System.out.println("Se ha escogido la tarifa dirigida a estudiantes. Se aplicara un descuento del 10%.");
                return precioBase * 0.9;
            }
            case 2 -> {
                System.out.println("Se ha escogido la tarifa dirigida a la tercera edad. Se aplicara un descuento del 15%.");
                return precioBase * 0.85;
            }
            case 0 -> {
                System.out.println("Sin descuento. Se aplicara la tarifa de Publico General.");
                return precioBase;
            }
            default -> {
                System.out.println("Por favor, ingrese un número válido.");
                return precioBase;
            }
        }
    }
    
    public static void main(String[] args) {
        
        String saltoDeLinea = System.lineSeparator();
        Scanner scanner = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        
        int seguirComprando = 1;
        int opcionMenuPrincipal = 0;
        int opcionMenuReserva;
        int promocion = 0;
        String reduccionElegida = "";
        
        final double precioVip = 30000;
        final double precioPlateaBaja = 15000;
        final double precioPlateaAlta = 18000;
        
        int filasVip = 3;
        int asientosVip = 4;
        int filasPlateaBaja = 3;
        int asientosPlateaBaja = 8;
        int filasPlateaAlta = 3;
        int asientosPlateaAlta = 12;
        
        boolean[] filaVip1 = new boolean[asientosVip];
        boolean[] filaVip2 = new boolean[asientosVip];
        boolean[] filaVip3 = new boolean[asientosVip];
        boolean[] filaPlateaBaja1 = new boolean[asientosPlateaBaja];
        boolean[] filaPlateaBaja2 = new boolean[asientosPlateaBaja];
        boolean[] filaPlateaBaja3 = new boolean[asientosPlateaBaja];
        boolean[] filaPlateaAlta1 = new boolean[asientosPlateaAlta];
        boolean[] filaPlateaAlta2 = new boolean[asientosPlateaAlta];
        boolean[] filaPlateaAlta3 = new boolean[asientosPlateaAlta];

        
        boolean[] filaActual = null;
        int filaSeleccionada1 = -1;
        boolean[] filaSeleccionada2 = null;
        int asientoSel = 0;
        
        double montoPago = 0;
        double vuelto;
        int menuCarrito;
        
        int h = 0;
        int j = 0;
        int l = 0;
        
        String[] carrito = new String[99];
        int[] cantidadesPorEntrada = new int[99];
        double[] historialPrecio = new double[99];
        String[] historialDescuento = new String[99];
        double totalCompras = 0;
        
        String[] carritoReserva = new String[99];
        int[] cantidadesPorEntradaReserva = new int[99];
        double[] historialPrecioReserva = new double[99];
        String[] historialDescuentoReserva = new String[99];
        double totalComprasReserva = 0;
        
        String[] carritoConfirmado = new String[99];
        int[] cantidadesPorEntradaConfirmado = new int[99];
        double[] historialPrecioConfirmado = new double[99];
        String[] historialDescuentoConfirmado = new String[99];
        double totalComprasConfirmado = 0;
        
        
        ArrayList<String> carritoB = new ArrayList<>();
        ArrayList<Integer> asiento = new ArrayList<>();
        ArrayList<Integer> cantidadesPorEntradaB = new ArrayList<>();
        ArrayList<Double> historialPrecioB = new ArrayList<>();
        ArrayList<String> historialDescuentoB = new ArrayList<>();
        
        
        int numeroTotalEntradas = 0;
        int numeroTotalEntradasReservadas = 0;
        
        int entradasSolicitadas;
        int entradasConfirmadas = 0;
        byte entradaElegida;
        String tipoEntradaElegida = "";
        String tarifaEntradaElegida = "";
        double valorEntradaElegida = 0;
        double totalValorEntradaElegida = 0;
        
        System.out.println("Hola. Bienvenido/a a la boleteria virtual del teatro Moro.");
        for (int i = 0;; i++) {
            
            System.out.println(saltoDeLinea + "Menu Principal:");
            System.out.println("1. Comprar entrada");
            System.out.println("2. Reservar entrada");
            System.out.println("3. Ver Promociones");
            System.out.println("4. Ver asientos disponibles");
            System.out.println("5. Ver carrito");
            System.out.println("6. Salir");
            System.out.print(saltoDeLinea + "Seleccione una opcion (Escriba 1, 2, 3, 4, 5, o 6 para desplazarse por el menu): ");

            opcionMenuPrincipal = scanner.nextInt();

            switch (opcionMenuPrincipal) {
                case 1 -> {
                    do {
                        System.out.println("Has seleccionado 'Comprar entrada'.");
                        System.out.println("Por favor, escribe el numero indicado para escoger el tipo de entrada que deseas. (1, 2, 3 o 4.)" + saltoDeLinea + saltoDeLinea + "=========TIPOS DE ENTRADAS=========" + saltoDeLinea + saltoDeLinea + "1- VIP =================== $" + precioVip + saltoDeLinea + "2- Platea baja =========== $" + precioPlateaBaja + saltoDeLinea + "3- Platea alta =========== $" + precioPlateaAlta + saltoDeLinea);
                        
                        do {
                            entradaElegida = scanner.nextByte();
                            
                            switch (entradaElegida) {
                                case 1 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector VIP." + saltoDeLinea + "valor: $" + precioVip + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo VIP desea comprar?");
                                    entradasSolicitadas = scanner.nextInt();
                                    numeroTotalEntradas += entradasSolicitadas;
                                    asientoSel = 0;
                                    
                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);
                                    
                                    while (entradasConfirmadas < entradasSolicitadas) {
                                        
                                        while (!seleccionarAsiento(scanner, filasVip, asientosVip, filaVip1, filaVip2, filaVip3)) {
                                        }
                                        
                                        mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);
                                        valorEntradaElegida = precioVip;
                                        tipoEntradaElegida = "Sector VIP";
                                        
                                        System.out.println("Desea comprar esta entrada con un descuento de promocion?" + saltoDeLinea + "(Escriba 1 para Estudiantes, 2 para Tercera edad. 0 Para no aplicar promocion.)");
                                        promocion = scanner.nextInt();
                                        totalValorEntradaElegida = calcularDescuento(precioVip, promocion);

                                        carritoB.add(j,tipoEntradaElegida);
                                        cantidadesPorEntradaB.add(j,entradasSolicitadas);
                                        
                                        asiento.add(j,asientoSeleccionado);
                                        historialPrecioB.add(j,totalValorEntradaElegida);
                                        historialDescuentoB.add(j,tarifaEntradaElegida);
                                        totalCompras += totalValorEntradaElegida;
                                        j = j + 1;
                                        entradasConfirmadas++;

                                        
                                    }
                                    System.out.println("Desea seguir comprando entradas? (1 = Si, 2 = No, volver al menu principal)");
                                    seguirComprando = scanner.nextInt();
                                    
                                    System.out.println("=== Resumen de compra ===");
                                    for ( i = 0; i < carritoB.size(); i++) {
                                        
                                        System.out.println("Numero de entrada: " + (i));
                                        System.out.println("Tipo: " + carritoB.get(i));
                                        System.out.println("Asiento: " + cantidadesPorEntradaB.get(i));
                                        System.out.println("Tipo de descuento: $" + historialDescuentoB.get(i));                                        
                                        System.out.println("Precio con descuento: $" + historialPrecioB.get(i));
                                        System.out.println("-----------------------");
                                    }                                   
                                    
                                    
                                }
                                
                                case 2 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector PlateaBaja." + saltoDeLinea + "valor: $" + precioPlateaBaja + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaBaja desea comprar?");
                                    entradasSolicitadas = scanner.nextInt();
                                    numeroTotalEntradas += entradasSolicitadas;
                                    asientoSel = 0;
                                    
                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);
                                    
                                    while (entradasConfirmadas < entradasSolicitadas) {
                                        
                                        while (!seleccionarAsiento(scanner, filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3)) {
                                        }
                                        
                                        mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);
                                        valorEntradaElegida = precioPlateaBaja;
                                        tipoEntradaElegida = "Sector PlateaBaja";
                                        
                                        System.out.println("Desea comprar esta entrada con un descuento de promocion?" + saltoDeLinea + "(Escriba 1 para Estudiantes, 2 para Tercera edad. 0 Para no aplicar promocion.)");
                                        promocion = scanner.nextInt();
                                        totalValorEntradaElegida = calcularDescuento(precioPlateaBaja, promocion);
                                        
                                        carrito[j] = tipoEntradaElegida;
                                        cantidadesPorEntrada[j] = entradasSolicitadas;
                                        historialPrecio[j] = totalValorEntradaElegida;
                                        historialDescuento[j] = tarifaEntradaElegida;
                                        totalCompras += totalValorEntradaElegida;
                                        j = j + 1;
                                        entradasConfirmadas++;
                                        
                                    }
                                    System.out.println("Desea seguir comprando entradas? (1 = Si, 2 = No, volver al menu principal)");
                                    seguirComprando = scanner.nextInt();
                                }
                                case 3 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector PlateaAlta." + saltoDeLinea + "valor: $" + precioPlateaAlta + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaAlta desea comprar?");
                                    entradasSolicitadas = scanner.nextInt();
                                    numeroTotalEntradas += entradasSolicitadas;
                                    asientoSel = 0;
                                    
                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);
                                    
                                    while (entradasConfirmadas < entradasSolicitadas) {
                                        
                                        while (!seleccionarAsiento(scanner, filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3)) {
                                        }
                                        
                                        mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);
                                        valorEntradaElegida = precioPlateaAlta;
                                        tipoEntradaElegida = "Sector PlateaBaja";
                                        
                                        System.out.println("Desea comprar esta entrada con un descuento de promocion?" + saltoDeLinea + "(Escriba 1 para Estudiantes, 2 para Tercera edad. 0 Para no aplicar promocion.)");
                                        promocion = scanner.nextInt();
                                        totalValorEntradaElegida = calcularDescuento(precioPlateaAlta, promocion);
                                        
                                        carrito[j] = tipoEntradaElegida;
                                        cantidadesPorEntrada[j] = entradasSolicitadas;
                                        historialPrecio[j] = totalValorEntradaElegida;
                                        historialDescuento[j] = tarifaEntradaElegida;
                                        totalCompras += totalValorEntradaElegida;
                                        j = j + 1;
                                        entradasConfirmadas++;
                                        
                                    }
                                    System.out.println("Desea seguir comprando entradas? (1 = Si, 2 = No, volver al menu principal)");
                                    seguirComprando = scanner.nextInt();
                                }                              
                                
                                default ->
                                    System.out.println("Invalido. Ingrgese un numero del 1 al 3.");
                            }                            
                        } while (seguirComprando == 1);
                        
                    } while (entradaElegida < 1 || entradaElegida > 3);                    
                }
                case 2 -> {
                    do {
                        System.out.println(saltoDeLinea + "===========================" + saltoDeLinea + "Has seleccionado 'Reservar entradas:'" + saltoDeLinea + saltoDeLinea + "=========RESERVA DE ENTRADAS=========" + saltoDeLinea + saltoDeLinea + "1- VIP =================== $" + precioVip + saltoDeLinea + "2- Platea baja =========== $" + precioPlateaBaja + saltoDeLinea + "3- Platea alta =========== $" + precioPlateaAlta + saltoDeLinea + "4- Confirmar reserva"+ saltoDeLinea + "5- Volver al Menu Principal" +saltoDeLinea + saltoDeLinea + "Por favor, escribe el numero indicado para desplazarte por el Menu. (1, 2, 3, 4 o 5.)");
                        opcionMenuReserva = scanner.nextInt();
                        switch (opcionMenuReserva) {
                            case 1 -> {
                                entradasConfirmadas = 0;
                                System.out.println("Has seleccionado una entrada para ingresar al sector VIP." + saltoDeLinea + "valor: $" + precioVip + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo VIP desea reservar?");
                                entradasSolicitadas = scanner.nextInt();
                                numeroTotalEntradasReservadas += entradasSolicitadas;
                                asientoSel = 0;
                                
                                System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);
                                
                                while (entradasConfirmadas < entradasSolicitadas) {
                                    
                                    while (!seleccionarReserva(scanner, filasVip, asientosVip, filaVip1, filaVip2, filaVip3)) {
                                    }
                                    
                                    mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);
                                    valorEntradaElegida = precioVip;
                                    tipoEntradaElegida = "Sector VIP";
                                    
                                    System.out.println("Desea reservar esta entrada con un descuento de promocion?" + saltoDeLinea + "(Escriba 1 para Estudiantes, 2 para Tercera edad. 0 Para no aplicar promocion.)");
                                    promocion = scanner.nextInt();
                                    totalValorEntradaElegida = calcularDescuento(precioVip, promocion);
                                    
                                    carritoReserva[h] = tipoEntradaElegida;
                                    cantidadesPorEntradaReserva[h] = entradasSolicitadas;
                                    historialPrecioReserva[h] = totalValorEntradaElegida;
                                    historialDescuentoReserva[h] = tarifaEntradaElegida;
                                    totalComprasReserva += totalValorEntradaElegida;
                                    h = h + 1;
                                    entradasConfirmadas++;
                                }
                                System.out.println("Desea seguir comprando entradas? (1 = Si, 0 = No, volver al menu principal)");
                                seguirComprando = scanner.nextInt();
                                
                            }
                            case 2 -> {
                                entradasConfirmadas = 0;
                                System.out.println("Has seleccionado una entrada para ingresar al sector PlateaBaja." + saltoDeLinea + "valor: $" + precioPlateaBaja + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaBaja desea reservar?");
                                entradasSolicitadas = scanner.nextInt();
                                numeroTotalEntradasReservadas += entradasSolicitadas;
                                asientoSel = 0;
                                
                                System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);
                                
                                while (entradasConfirmadas < entradasSolicitadas) {
                                    
                                    while (!seleccionarAsiento(scanner, filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3)) {
                                    }
                                    
                                    mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);
                                    valorEntradaElegida = precioPlateaBaja;
                                    tipoEntradaElegida = "Sector PlateaBaja";
                                    
                                    System.out.println("Desea reservar esta entrada con un descuento de promocion?" + saltoDeLinea + "(Escriba 1 para Estudiantes, 2 para Tercera edad. 0 Para no aplicar promocion.)");
                                    promocion = scanner.nextInt();
                                    totalValorEntradaElegida = calcularDescuento(precioPlateaBaja, promocion);
                                    
                                    carrito[j] = tipoEntradaElegida;
                                    cantidadesPorEntrada[j] = entradasSolicitadas;
                                    historialPrecio[j] = totalValorEntradaElegida;
                                    historialDescuento[j] = tarifaEntradaElegida;
                                    totalCompras += totalValorEntradaElegida;
                                    j = j + 1;
                                    entradasConfirmadas++;
                                    
                                }
                                System.out.println("Desea seguir comprando entradas? (1 = Si, 2 = No, volver al menu principal)");
                                seguirComprando = scanner.nextInt();
                            }
                            case 3 -> {
                                entradasConfirmadas = 0;
                                System.out.println("Has seleccionado una entrada para ingresar al sector PlateaAlta." + saltoDeLinea + "valor: $" + precioPlateaAlta + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaAlta desea reservar?");
                                entradasSolicitadas = scanner.nextInt();
                                numeroTotalEntradasReservadas += entradasSolicitadas;
                                asientoSel = 0;
                                
                                System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);
                                
                                while (entradasConfirmadas < entradasSolicitadas) {
                                    
                                    while (!seleccionarAsiento(scanner, filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3)) {
                                    }
                                    
                                    mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);
                                    valorEntradaElegida = precioPlateaAlta;
                                    tipoEntradaElegida = "Sector PlateaBaja";
                                    
                                    System.out.println("Desea reservar esta entrada con un descuento de promocion?" + saltoDeLinea + "(Escriba 1 para Estudiantes, 2 para Tercera edad. 0 Para no aplicar promocion.)");
                                    promocion = scanner.nextInt();
                                    totalValorEntradaElegida = calcularDescuento(precioPlateaAlta, promocion);
                                    
                                    carrito[j] = tipoEntradaElegida;
                                    cantidadesPorEntrada[j] = entradasSolicitadas;
                                    historialPrecio[j] = totalValorEntradaElegida;
                                    historialDescuento[j] = tarifaEntradaElegida;
                                    totalCompras += totalValorEntradaElegida;
                                    j = j + 1;
                                    entradasConfirmadas++;
                                    
                                }
                                System.out.println("Desea seguir comprando entradas? (1 = Si, 2 = No, volver al menu principal)");
                                seguirComprando = scanner.nextInt();
                            }
                            
                            case 4 -> {
                                reservaPendiente = true;
                                System.out.println("Confirmando reservas de entradas... ");
                                if (reservaPendiente == true) {
                                    System.out.println("Reserva Confirmada" + "fila :" + reservaFila + "Asiento :" + reservaAsiento);
                                    timerReserva.cancel();
                                    reservaPendiente = false;
                                    reservaFila = -1;
                                    reservaAsiento = 1;
                                    
                                    for (l = 0; l < numeroTotalEntradas; l++) {
                                        carritoConfirmado[l] = carritoReserva[l];
                                        cantidadesPorEntradaConfirmado[l] = cantidadesPorEntradaReserva[l];
                                        historialPrecioConfirmado[l] = historialPrecioReserva[l];
                                        historialDescuentoConfirmado[l] = historialDescuentoReserva[l];
                                        totalComprasConfirmado = totalComprasReserva;
                                    }
                                    break;
                                } else {
                                    System.out.println("No hay reservas pendientes por confirmar.");
                                }
                            }
                            case 5 -> {
                                seguirComprando = 0;
                                break;
                            }
                        }
                    } while (seguirComprando == 1);
                }
                
                case 3 -> {
                    do {
                        System.out.println(saltoDeLinea + "===========================" + saltoDeLinea + "Has seleccionado 'Ver Promociones:'" + saltoDeLinea + saltoDeLinea + "- Descuento del 10% dirigido a estudiantes" + saltoDeLinea + "- Descuento del 15% dirigido a adultos mayores" + saltoDeLinea);
                        
                        System.out.println(saltoDeLinea + "Escriba 0 para volver al Menu Principal)");
                        seguirComprando = scanner.nextInt();
                        
                        while (seguirComprando != 0) {
                            System.out.println("Invalido. Escriba 0 para volver al Menu Principal");
                            seguirComprando = scanner.nextInt();
                        }
                    } while (seguirComprando != 0);
                    System.out.println("Volviendo..." + saltoDeLinea + "===========================" + saltoDeLinea);
                }
                case 4 -> {
                    do {
                        System.out.println(saltoDeLinea + "                    ========= Asientos disponibles =========" + saltoDeLinea);
                        
                        for (int f = 1; f <= filasVip; f++) {
                            switch (f) {
                                case 1 ->
                                    filaActual = filaVip1;
                                case 2 ->
                                    filaActual = filaVip2;
                                case 3 ->
                                    filaActual = filaVip3;
                                default -> {
                                }
                            }
                            
                            System.out.print("Fila VIP" + f + ":                        ");
                            
                            for (int a = 0; a < asientosVip; a++) {
                                if (filaActual[a]) {
                                    System.out.print("[X]");
                                } else {
                                    System.out.print("[O]");
                                }
                            }
                            System.out.println();
                        }
                        
                        for (int f = 1; f <= filasPlateaBaja; f++) {
                            switch (f) {
                                case 1 ->
                                    filaActual = filaPlateaBaja1;
                                case 2 ->
                                    filaActual = filaPlateaBaja2;
                                case 3 ->
                                    filaActual = filaPlateaBaja3;
                                default -> {
                                }
                            }
                            
                            System.out.print("Fila Platea Baja" + f + ":          ");
                            
                            for (int a = 0; a < asientosPlateaBaja; a++) {
                                if (filaActual[a]) {
                                    System.out.print("[X]");
                                } else {
                                    System.out.print("[O]");
                                }
                            }
                            System.out.println();
                        }
                        
                        for (int f = 1; f <= filasPlateaAlta; f++) {
                            
                            switch (f) {
                                case 1 ->
                                    filaActual = filaPlateaAlta1;
                                case 2 ->
                                    filaActual = filaPlateaAlta2;
                                case 3 ->
                                    filaActual = filaPlateaAlta3;
                                default -> {
                                }
                            }
                            
                            System.out.print("Fila Platea Alta" + f + ":    ");
                            
                            for (int a = 0; a < asientosPlateaAlta; a++) {
                                if (filaActual[a]) {
                                    System.out.print("[X]");
                                } else {
                                    System.out.print("[O]");
                                }
                            }
                            System.out.println();
                        }
                        System.out.println(saltoDeLinea + "Escriba 0 para volver al Menu Principal)");
                        seguirComprando = scanner.nextInt();
                        
                        while (seguirComprando != 0) {
                            System.out.println("Invalido. Escriba 0 para volver al Menu Principal");
                            seguirComprando = scanner.nextInt();
                        }
                    } while (seguirComprando != 0);
                    System.out.println("Volviendo..." + saltoDeLinea + "===========================" + saltoDeLinea);
                }
                
                case 5 -> {
                    System.out.println(saltoDeLinea + "======Carrito de Compras======" + saltoDeLinea);
                    
                    System.out.println("Contenido del carrito:");
                    
                    for (int k = 0; k < carrito.length; k++) {
                        if (carrito[k] != null) {
                            System.out.println("[n" + (k + 1) + "] " + carrito[k] + " " + historialDescuento[k] + " $" + historialPrecio[k]);
                        }
                    }
                    for (int m = 0; m < carritoConfirmado.length; m++) {
                        if (carritoConfirmado[m] != null) {
                            System.out.println("[n" + (numeroTotalEntradasReservadas + m) + "] " + carritoConfirmado[m] + " " + historialDescuentoConfirmado[m] + " $" + historialPrecioConfirmado[m]);
                        }
                    }
                    
                    System.out.println("Escriba 1 para Pagar, o 0 Salir al Menu Principal.");
                    menuCarrito = scanner.nextInt();
                    
                    if (menuCarrito == 0) {
                        
                        System.out.println("Volviendo..." + saltoDeLinea + "===========================");
                        break;
                        
                    } else if (menuCarrito == 1) {
                        do {
                            System.out.println("Total de la compra = $" + (totalCompras + totalComprasConfirmado) + saltoDeLinea + "Por favor. Ingrese el monto para realizar el pago.");
                            montoPago = scanner.nextDouble();

                            if (montoPago == (totalCompras + totalComprasConfirmado)) {

                                System.out.println(saltoDeLinea + "===========================Boleta de compra===========================");
                                
                                for (int k = 0; k < carrito.length; k++) {
                                    if (carrito[k] != null) {
                                        System.out.println("[Boleto n" + (k + 1) + "] " + carrito[k] + " " + historialDescuento[k] + "________________$" + historialPrecio[k]);
                                    }
                                }
                                for (int m = 0; m < carritoConfirmado.length; m++) {
                                    if (carritoConfirmado[m] != null) {
                                        System.out.println("[Boleto n" + (numeroTotalEntradasReservadas + m) + "] " + carritoConfirmado[m] + " " + historialDescuentoConfirmado[m] + " $$________________" + historialPrecioConfirmado[m]);
                                    }
                                }
                                System.out.println("Total__________________________________$" + (totalCompras + totalComprasConfirmado));
                                
                                System.out.println("Muchas gracias por tu compra en la boleteria virtual de Teatro Moro. Vuelve pronto!");
                                return;

                            } else if (montoPago > (totalCompras + totalComprasConfirmado)) {
                                vuelto = montoPago - totalCompras;
                                
                                System.out.println(saltoDeLinea + "================Boleta de compra================");
                                
                                for (int k = 0; k < carrito.length; k++) {
                                    if (carrito[k] != null) {
                                        System.out.println("[Boleto n" + (k + 1) + "] " + carrito[k] + " " + historialDescuento[k] + "________________$" + historialPrecio[k]);
                                    }
                                }
                                for (int m = 0; m < carritoConfirmado.length; m++) {
                                    if (carritoConfirmado[m] != null) {
                                        System.out.println("[Boleto n" + (numeroTotalEntradasReservadas + m) + "] " + carritoConfirmado[m] + " " + historialDescuentoConfirmado[m] + "________________$" + historialPrecioConfirmado[m]);
                                    }
                                }
                                System.out.println("Total__________________________________$" + (totalCompras + totalComprasConfirmado));
                                System.out.println("Compra realizada con exito." + saltoDeLinea + "Tu vuelto es: $" + vuelto + saltoDeLinea + saltoDeLinea + "Muchas gracias por tu compra en la boleteria virtual de Teatro Moro. Vuelve pronto!");                               
                                return;
                                
                            } else if (montoPago < (totalCompras + totalComprasConfirmado)) {
                                
                                System.out.println("El monto ingresado no es suficiente. El pago no se pudo realizar." + saltoDeLinea + "Desea reintentarlo? ( 1 para volver a intentarlo, 0 para salir al Menu Principal)");
                                seguirComprando = scanner.nextInt();
                                
                                if (seguirComprando == 1) {
                                    System.out.println("Por favor, ingrese el monto para realizar el pago. $" + totalCompras);
                                    
                                } else if (seguirComprando == 0) {
                                    System.out.println("Volviendo..." + saltoDeLinea + "===========================" + saltoDeLinea);
                                    break;
                                }
                            }
                        } while ((seguirComprando == 1));
                    }
                }
                
                case 6 -> {
                    System.out.println("Muchas gracias por por utilizar el sistema vitual de Teatro Moro. Vuelva pronto" + saltoDeLinea);
                    return;
                }
                default ->
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }
    }
}
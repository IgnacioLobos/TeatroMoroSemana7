package teatromorosemana7;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.ArrayList;

public class TeatroMoroSemana7 {

    private static final int filasVip = 3;
    private static final int asientosVip = 4;
    private static final boolean[] filaVip1 = new boolean[asientosVip];
    private static final boolean[] filaVip2 = new boolean[asientosVip];
    private static final boolean[] filaVip3 = new boolean[asientosVip];

    private static final int filasPlateaBaja = 3;
    private static final int asientosPlateaBaja = 8;
    private static final boolean[] filaPlateaBaja1 = new boolean[asientosPlateaBaja];
    private static final boolean[] filaPlateaBaja2 = new boolean[asientosPlateaBaja];
    private static final boolean[] filaPlateaBaja3 = new boolean[asientosPlateaBaja];

    private static final int filasPlateaAlta = 3;
    private static final int asientosPlateaAlta = 12;
    private static final boolean[] filaPlateaAlta1 = new boolean[asientosPlateaAlta];
    private static final boolean[] filaPlateaAlta2 = new boolean[asientosPlateaAlta];
    private static final boolean[] filaPlateaAlta3 = new boolean[asientosPlateaAlta];

    public static void mostrarPlanoAsientos(int tipoFilas, int asientosPorFila, boolean[]... filas) {        
        for (int f = 0; f < tipoFilas; f++) {
            switch (asientosPorFila) {
                case asientosVip ->
                    System.out.print("Fila VIP" + f + ":                        ");
                case asientosPlateaBaja ->
                    System.out.print("Fila Platea Baja" + f + ":          ");
                case asientosPlateaAlta ->
                    System.out.print("Fila Platea Alta" + f + ":    ");
                default -> {
                }
            }
            for (int a = 0; a < asientosPorFila; a++) {
                if (filas[f][a]) {
                    System.out.print("[X]");
                } else {
                    System.out.print("[O]");
                }
            }
            System.out.println();
        }       
    }

    private static int filaSeleccionada;
    private static int asientoSeleccionado;

    public static boolean seleccionarAsiento(Scanner sc, int tipoFilas, int asientosPorFila, boolean[]... filas) {
        String saltoDeLinea = System.lineSeparator();
        System.out.print("\nSeleccione una fila (1-" + tipoFilas + ") o 0 para salir: ");
        filaSeleccionada = sc.nextInt();

        if (filaSeleccionada == 0) {
            return false;
        } else if (filaSeleccionada < 1 || filaSeleccionada > tipoFilas) {
            System.out.println("Fila inválida.");
            return false;
        }

        System.out.print("Seleccione un asiento (1-" + asientosPorFila + "): ");
        asientoSeleccionado = sc.nextInt();

        if (asientoSeleccionado < 1 || asientoSeleccionado > asientosPorFila) {
            System.out.println("Asiento inválido.");
            return false;
        }

        boolean[] fila = filas[filaSeleccionada - 1];
        
        if (fila[asientoSeleccionado - 1]) {
            System.out.println("Ese asiento ya está ocupado.");
            return false;
        } else {
            fila[asientoSeleccionado - 1] = true;
            System.out.println("Compra exitosa. Fila: " + filaSeleccionada + " Asiento: " + asientoSeleccionado + saltoDeLinea);
            return true;
        }
    }

    static Timer timerReserva = null;
    static boolean reservaPendiente = false;
    static int reservaFila = -1;
    static int reservaAsiento = -1;

    public static boolean seleccionarReserva(Scanner sc, int tipoFilas, int asientosPorFila, boolean[]... filas) {
        System.out.print("\nSeleccione una fila (1-" + tipoFilas + ") o 0 para salir: ");
        filaSeleccionada = sc.nextInt();

        if (filaSeleccionada == 0) {
            return false;
        } else if (filaSeleccionada < 1 || filaSeleccionada > tipoFilas) {
            System.out.println("Fila inválida.");
            return false;
        }

        System.out.print("Seleccione un asiento (1-" + asientosPorFila + "): ");
        asientoSeleccionado = sc.nextInt();

        if (asientoSeleccionado < 1 || asientoSeleccionado > asientosPorFila) {
            System.out.println("Asiento inválido.");
            return false;
        }

        final boolean[] fila = filas[filaSeleccionada - 1];

        if (fila[asientoSeleccionado - 1]) {
            System.out.println("Ese asiento ya está ocupado.");
            return false;
        } else {
            fila[asientoSeleccionado - 1] = true;
            System.out.println("Reserva exitosa: Fila " + filaSeleccionada + " Asiento: " + asientoSeleccionado);

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

    private static String tarifaSeleccionada = "";

    public static double calcularDescuento(Scanner sc, double precioBase, int promocion) {
        String saltoDeLinea = System.lineSeparator();
        System.out.println(saltoDeLinea +"Desea comprar esta entrada con un descuento de promocion?" + saltoDeLinea + "(Escriba 1 para Estudiantes, 2 para Tercera edad. 0 Para no aplicar promocion.)");
        promocion = sc.nextInt();

        switch (promocion) {
            case 1 -> {
                System.out.println("Se ha escogido la tarifa dirigida a estudiantes. Se aplicara un descuento del 10%.");
                tarifaSeleccionada = "Estudiante";
                return precioBase * 0.9;
            }
            case 2 -> {
                System.out.println("Se ha escogido la tarifa dirigida a la tercera edad. Se aplicara un descuento del 15%.");
                tarifaSeleccionada = "Tercera Edad";
                return precioBase * 0.85;
            }
            case 0 -> {
                System.out.println("Sin descuento. Se aplicara la tarifa de Publico General.");
                tarifaSeleccionada = "Publico General";
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
        Scanner sc = new Scanner(System.in);

        int promocion = 0;
        int opcionMenu = 0;
        int opcionMenuRetorno = 0;
        int opcionEliminarEntrada = 0;

        String sectorVip = "Sector VIP";
        String PlateaBaja = "Platea Baja";
        String PlateaAlta = "Platea Alta";
        
        final double precioVip = 30000;
        final double precioPlateaBaja = 15000;
        final double precioPlateaAlta = 18000;

        int asientoSel = 0;

        int entradasSolicitadas;
        int entradasConfirmadas = 0;
        int numeroTotalEntradas = 0;
        int numeroTotalEntradasReservadas = 0;

        ArrayList<String> historialTipoDeEntrada = new ArrayList<>();
        ArrayList<Integer> historialFilas = new ArrayList<>();
        ArrayList<Integer> historialAsientos = new ArrayList<>();
        ArrayList<String> historialTarifas = new ArrayList<>();
        ArrayList<Double> historialPrecios = new ArrayList<>();

        ArrayList<Double> historialPrecioFinal = new ArrayList<>();

        ArrayList<String> historialTipoDeEntradaReserva = new ArrayList<>();
        ArrayList<Integer> historialFilasReserva = new ArrayList<>();
        ArrayList<Integer> historialAsientosReserva = new ArrayList<>();
        ArrayList<String> historialTarifasReserva = new ArrayList<>();
        ArrayList<Double> historialPreciosReserva = new ArrayList<>();

        ArrayList<Double> historialPrecioFinalReserva = new ArrayList<>();

        double totalValorEntradaSeleccionada = 0;
        double totalCompras = 0;
        double montoPago = 0;
        double vuelto = 0;

        int h = 0;
        int j = 0;

        System.out.println("Hola. Bienvenido/a a la boleteria virtual del teatro Moro.");
        for (int i = 0;; i++) {

            System.out.println(saltoDeLinea + "=========MENU PRINCIPAL=========" + saltoDeLinea);
            System.out.println("1. Comprar entrada");
            System.out.println("2. Reservar entrada");
            System.out.println("3. Confirmar reservas al carrito");
            System.out.println("4. Ver carrito");
            System.out.println("5. Ver asientos disponibles");
            System.out.println("6. Ver Promociones");
            System.out.println("7. Salir");
            System.out.print(saltoDeLinea + "Seleccione una opcion (Escriba 1, 2, 3, 4, 5, o 6 para desplazarse por el menu): ");

            opcionMenu = sc.nextInt();
            System.out.println(saltoDeLinea);

            switch (opcionMenu) {
                case 1 -> {
                    System.out.println("Has seleccionado 'Compra de entradas'.");
                        do {
                            System.out.println(saltoDeLinea + "=========COMPRA DE ENTRADAS=========" + saltoDeLinea + saltoDeLinea + "1- VIP =================== $" + precioVip + saltoDeLinea + "2- Platea baja =========== $" + precioPlateaBaja + saltoDeLinea + "3- Platea alta =========== $" + precioPlateaAlta + saltoDeLinea + "4- Volver al Menu Principal" + saltoDeLinea + saltoDeLinea + "Por favor, escribe el numero indicado para escoger el tipo de entrada que deseas comprar. (1, 2, 3 o 4 = Volver al Menu Principal.)");
                            opcionMenu = sc.nextByte();
                            
                            switch (opcionMenu) {
                                case 1 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector VIP." + saltoDeLinea + "valor: $" + precioVip + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo VIP desea comprar?");
                                    entradasSolicitadas = sc.nextInt();
                                    numeroTotalEntradas += entradasSolicitadas;
                                    asientoSel = 0;

                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);

                                    while (entradasConfirmadas < entradasSolicitadas) {

                                        while (!seleccionarAsiento(sc, filasVip, asientosVip, filaVip1, filaVip2, filaVip3)) {
                                        }

                                        mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);
                                        totalValorEntradaSeleccionada = calcularDescuento(sc, precioVip, promocion);

                                        historialTipoDeEntrada.add(j, sectorVip);
                                        historialAsientos.add(j, asientoSeleccionado);
                                        historialFilas.add(j, filaSeleccionada);
                                        historialTarifas.add(j, tarifaSeleccionada);
                                        historialPrecios.add(j, precioVip);

                                        historialPrecioFinal.add(j, totalValorEntradaSeleccionada);
                                        j = j + 1;
                                        entradasConfirmadas++;
                                    }

                                    System.out.println("Desea seguir comprando entradas? (1 = Si, 0 = No, volver al menu principal)");
                                    opcionMenuRetorno = sc.nextInt();

                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Error... Ingrgese una opcion valida. Desea seguir comprando entradas? (1 = Si, 0 = No, volver al menu principal");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                                case 2 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector PlateaBaja." + saltoDeLinea + "valor: $" + precioPlateaBaja + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaBaja desea comprar?");
                                    entradasSolicitadas = sc.nextInt();
                                    numeroTotalEntradas += entradasSolicitadas;
                                    asientoSel = 0;

                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);

                                    while (entradasConfirmadas < entradasSolicitadas) {

                                        while (!seleccionarAsiento(sc, filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3)) {
                                        }

                                        mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);
                                        totalValorEntradaSeleccionada = calcularDescuento(sc, precioPlateaBaja, promocion);

                                        historialTipoDeEntrada.add(j, PlateaBaja);
                                        historialAsientos.add(j, asientoSeleccionado);
                                        historialFilas.add(j, filaSeleccionada);
                                        historialTarifas.add(j, tarifaSeleccionada);
                                        historialPrecios.add(j, precioPlateaBaja);

                                        historialPrecioFinal.add(j, totalValorEntradaSeleccionada);
                                        j = j + 1;
                                        entradasConfirmadas++;
                                    }

                                    System.out.println("Desea seguir comprando entradas? (1 = Si, 0 = No, volver al menu principal)");
                                    opcionMenuRetorno = sc.nextInt();

                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Error... Ingrgese una opcion valida. Desea seguir comprando entradas? (1 = Si, 0 = No, volver al menu principal");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                                case 3 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector PlateaAlta." + saltoDeLinea + "valor: $" + precioPlateaAlta + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaAlta desea comprar?");
                                    entradasSolicitadas = sc.nextInt();
                                    numeroTotalEntradas += entradasSolicitadas;
                                    asientoSel = 0;

                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);

                                    while (entradasConfirmadas < entradasSolicitadas) {

                                        while (!seleccionarAsiento(sc, filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3)) {
                                        }

                                        mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);
                                        totalValorEntradaSeleccionada = calcularDescuento(sc, precioPlateaAlta, promocion);

                                        historialTipoDeEntrada.add(j, PlateaAlta);
                                        historialAsientos.add(j, asientoSeleccionado);
                                        historialFilas.add(j, filaSeleccionada);
                                        historialTarifas.add(j, tarifaSeleccionada);
                                        historialPrecios.add(j, precioPlateaAlta);

                                        historialPrecioFinal.add(j, totalValorEntradaSeleccionada);
                                        j = j + 1;
                                        entradasConfirmadas++;
                                    }

                                    System.out.println("Desea seguir comprando entradas? (1 = Si, 0 = No, volver al menu principal)");
                                    opcionMenuRetorno = sc.nextInt();

                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Error... Ingrgese una opcion valida. Desea seguir comprando entradas? (1 = Si, 0 = No, volver al menu principal");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                                case 4 -> {
                                break;
                                }
                                default -> {
                                    System.out.println("ERROR. Numero ingresado invalido. Ingrese 1 para volver a intentar, 0 para Volver al Menu Principal.");
                                    opcionMenuRetorno = sc.nextInt();
                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Invalido... Ingrese 1 para volver a intentar, 0 para Volver al Menu Principal.");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                            }
                        } while (opcionMenuRetorno != 0);
                        System.out.println("Volviendo al Menu Principal..."+saltoDeLinea+ "---------oooooooooooooo---------");
                }
                case 2 -> {
                    System.out.println("Has seleccionado 'Reservar entradas'.");
                        do {
                            System.out.println(saltoDeLinea + "=========RESERVA DE ENTRADAS=========" + saltoDeLinea + saltoDeLinea + "1- VIP =================== $" + precioVip + saltoDeLinea + "2- Platea baja =========== $" + precioPlateaBaja + saltoDeLinea + "3- Platea alta =========== $" + precioPlateaAlta + saltoDeLinea + "4- Volver al Menu Principal" + saltoDeLinea + saltoDeLinea + "Por favor, escribe el numero indicado para desplazarte por el Menu. (1, 2, 3, o 4 = Volver al Menu Principal.)");
                            opcionMenu = sc.nextInt();
                            
                            switch (opcionMenu) {
                                case 1 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector VIP." + saltoDeLinea + "valor: $" + precioVip + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo VIP desea reservar?");
                                    entradasSolicitadas = sc.nextInt();
                                    numeroTotalEntradasReservadas += entradasSolicitadas;
                                    asientoSel = 0;

                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);

                                    while (entradasConfirmadas < entradasSolicitadas) {

                                        while (!seleccionarReserva(sc, filasVip, asientosVip, filaVip1, filaVip2, filaVip3)) {
                                        }

                                        mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);
                                        totalValorEntradaSeleccionada = calcularDescuento(sc, precioVip, promocion);

                                        historialTipoDeEntradaReserva.add(h, sectorVip);
                                        historialAsientosReserva.add(h, asientoSeleccionado);
                                        historialFilasReserva.add(h, filaSeleccionada);
                                        historialTarifasReserva.add(h, tarifaSeleccionada);
                                        historialPreciosReserva.add(h, precioVip);

                                        historialPrecioFinal.add(h, totalValorEntradaSeleccionada);
                                        h = h + 1;
                                        entradasConfirmadas++;
                                        reservaPendiente = true;

                                    }
                                    
                                    System.out.println("Desea seguir reservando entradas? (1 = Si, 0 = No, volver al menu principal)");
                                    opcionMenuRetorno = sc.nextInt();

                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Error... Ingrgese una opcion valida. Desea seguir reservando entradas? (1 = Si, 0 = No, volver al menu principal");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                                case 2 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector PlateaBaja." + saltoDeLinea + "valor: $" + precioPlateaBaja + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaBaja desea reservar?");
                                    entradasSolicitadas = sc.nextInt();
                                    numeroTotalEntradasReservadas += entradasSolicitadas;
                                    asientoSel = 0;

                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);

                                    while (entradasConfirmadas < entradasSolicitadas) {

                                        while (!seleccionarReserva(sc, filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3)) {
                                        }

                                        mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);
                                        totalValorEntradaSeleccionada = calcularDescuento(sc, precioPlateaBaja, promocion);

                                        historialTipoDeEntradaReserva.add(h, PlateaBaja);
                                        historialAsientosReserva.add(h, asientoSeleccionado);
                                        historialFilasReserva.add(h, filaSeleccionada);
                                        historialTarifasReserva.add(h, tarifaSeleccionada);
                                        historialPreciosReserva.add(h, precioPlateaBaja);

                                        historialPrecioFinal.add(h, totalValorEntradaSeleccionada);
                                        h = h + 1;
                                        entradasConfirmadas++;
                                        reservaPendiente = true;
                                    }
                                    
                                    System.out.println("Desea seguir reservando entradas? (1 = Si, 0 = No, volver al menu principal)");
                                    opcionMenuRetorno = sc.nextInt();

                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Error... Ingrgese una opcion valida. Desea seguir reservando entradas? (1 = Si, 0 = No, volver al menu principal");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                                case 3 -> {
                                    entradasConfirmadas = 0;
                                    System.out.println("Has seleccionado una entrada para ingresar al sector PlateaAlta." + saltoDeLinea + "valor: $" + precioPlateaAlta + saltoDeLinea + saltoDeLinea + "Cuantas entradas de tipo PlateaAlta desea reservar?");
                                    entradasSolicitadas = sc.nextInt();
                                    numeroTotalEntradasReservadas += entradasSolicitadas;
                                    asientoSel = 0;

                                    System.out.println("(Plano de la sala (X = ocupado, O = libre)");
                                    mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);

                                    while (entradasConfirmadas < entradasSolicitadas) {

                                        while (!seleccionarReserva(sc, filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3)) {
                                        }

                                        mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);
                                        totalValorEntradaSeleccionada = calcularDescuento(sc, precioPlateaAlta, promocion);

                                        historialTipoDeEntradaReserva.add(h, PlateaAlta);
                                        historialAsientosReserva.add(h, asientoSeleccionado);
                                        historialFilasReserva.add(h, filaSeleccionada);
                                        historialTarifasReserva.add(h, tarifaSeleccionada);
                                        historialPreciosReserva.add(h, precioPlateaAlta);

                                        historialPrecioFinalReserva.add(h, totalValorEntradaSeleccionada);
                                        h = h + 1;
                                        entradasConfirmadas++;
                                        reservaPendiente = true;
                                    }
                                    
                                    System.out.println("Desea seguir reservando entradas? (1 = Si, 0 = No, volver al menu principal)");
                                    opcionMenuRetorno = sc.nextInt();
                                    
                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Error... Ingrgese una opcion valida. Desea seguir reservando entradas? (1 = Si, 0 = No, volver al menu principal");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                                case 4 -> {
                                break;
                                }
                                default -> {
                                    System.out.println("ERROR. Numero ingresado invalido. Ingrese 1 para volver a intentar, 0 para Volver al Menu Principal.");
                                    opcionMenuRetorno = sc.nextInt();
                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Invalido... Ingrese 1 para volver a intentar, 0 para Volver al Menu Principal.");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                            }
                        } while (opcionMenuRetorno != 0);
                        System.out.println("Volviendo al Menu Principal..."+saltoDeLinea+ "---------oooooooooooooo---------");
                }               
                case 3 -> {
                    System.out.println("Has seleccionado 'Confirmar reserva de entradas'.");
                    if (reservaPendiente == true) {
                        
                        timerReserva.cancel();
                        reservaPendiente = false;
                        reservaFila = -1;
                        reservaAsiento = 1;

                        historialTipoDeEntrada.addAll(historialTipoDeEntradaReserva);
                        historialAsientos.addAll(historialAsientosReserva);
                        historialFilas.addAll(historialFilasReserva);
                        historialTarifas.addAll(historialTarifasReserva);
                        historialPrecios.addAll(historialPreciosReserva);
                        historialPrecioFinal.addAll(historialPrecioFinalReserva);
                        
                        System.out.println("Confirmando reservas de entradas... Reservas confirmadas." + saltoDeLinea + saltoDeLinea + "Escriba 0 para volver al Menu Principal");
                        opcionMenuRetorno = sc.nextInt();

                        while (opcionMenuRetorno != 0) {
                            System.out.println("Invalido... 0 = Salir al Menu Principal");
                            opcionMenuRetorno = sc.nextInt();
                        }
                        break;
                    }
                    else {
                        System.out.println("No hay reservas pendientes por confirmar." + saltoDeLinea + saltoDeLinea + "Escriba 0 para volver al Menu Principal");
                        opcionMenuRetorno = sc.nextInt();

                        while (opcionMenuRetorno != 0) {
                            System.out.println("Invalido... 0 = Salir al Menu Principal");
                            opcionMenuRetorno = sc.nextInt();
                        }
                    }
                System.out.println("Volviendo al Menu Principal..."+saltoDeLinea+ "---------oooooooooooooo---------");
                }
                case 4 -> {
                    System.out.println("Has seleccionado 'Ver Carrito de Compras'." + saltoDeLinea);
                        do {
                            totalCompras = 0;
                            System.out.println(saltoDeLinea + "======Carrito de Compras======" + saltoDeLinea);
                            System.out.println("Contenido del carrito:");

                            for (int k = 0; k < historialTipoDeEntrada.size(); k++) {

                                System.out.println("[" + (k + 1) + "]" + " Asiento: " + historialFilas.get(k) + "-" + historialAsientos.get(k) + " " + historialTipoDeEntrada.get(k) + ", " + historialTarifas.get(k) + "  $" + historialPrecioFinal.get(k));
                                System.out.println("----------------------------------------------");
                            }
                            for (double entradas : historialPrecioFinal) {
                                totalCompras += entradas;
                            }
                            System.out.println("Total:___________________________________$" + totalCompras+saltoDeLinea);

                            System.out.println("Ingrese una opcion. 1 = Pagar, 2 = Eliminar entradas, 3 = Salir al Menu Principal");
                            opcionMenu = sc.nextInt();

                            switch (opcionMenu) {
                                case 1 -> {
                                    System.out.println("Por favor. Ingrese el monto para realizar el pago.");
                                    do {                                       
                                        montoPago = sc.nextDouble();

                                        if (montoPago == totalCompras) {

                                            System.out.println(saltoDeLinea + "===========================Boleta de compra===========================");

                                            for (int k = 0; k < historialTipoDeEntrada.size(); k++) {

                                                System.out.println("Entrada Nro.: " + (k + 1));
                                                System.out.println("Tipo: " + historialTipoDeEntrada.get(k));
                                                System.out.println("Fila: " + historialFilas.get(k));
                                                System.out.println("Asiento: " + historialAsientos.get(k));
                                                System.out.println("Tarifa: " + historialTarifas.get(k));
                                                System.out.println("Precio con descuento: $" + historialPrecioFinal.get(k));
                                                System.out.println("-----------------------");
                                            }

                                            if (historialTipoDeEntrada.size() > 1) {
                                                System.out.println("Total:________________$" + totalCompras);
                                            }

                                            System.out.println("Compra realizada con exito..." + saltoDeLinea + saltoDeLinea + "Muchas gracias por utilizar la boleteria virtual de Teatro Moro. Vuelve pronto!");
                                            return;
                                        } 
                                        else if (montoPago > totalCompras) {
                                            vuelto = montoPago - totalCompras;

                                            System.out.println(saltoDeLinea + "================Boleta de compra================");

                                            for (int k = 0; k < historialTipoDeEntrada.size(); k++) {

                                                System.out.println("Entrada Nro.: " + (k + 1));
                                                System.out.println("Tipo: " + historialTipoDeEntrada.get(k));
                                                System.out.println("Fila: " + historialFilas.get(k));
                                                System.out.println("Asiento: " + historialAsientos.get(k));
                                                System.out.println("Tarifa: " + historialTarifas.get(k));
                                                System.out.println("Precio con descuento: $" + historialPrecioFinal.get(k));
                                                System.out.println("-----------------------" + saltoDeLinea);
                                            }
                                            if (historialTipoDeEntrada.size() > 1) {
                                                System.out.println("Total:________________$" + totalCompras);
                                            }
                                            System.out.println("Monto ingresado:______$" + montoPago + saltoDeLinea + "Tu vuelto es:_________$" + vuelto + saltoDeLinea + saltoDeLinea + "Compra realizada con exito!" + saltoDeLinea + "Muchas gracias por utilizar la boleteria virtual de Teatro Moro. Vuelve pronto!");
                                            return;
                                        } 
                                        else if (montoPago < totalCompras) {

                                            System.out.println("El monto ingresado no es suficiente. El pago no se pudo realizar." + saltoDeLinea + "Desea reintentarlo? ( 1 para volver a intentarlo, 0 para salir al Menu Principal)");
                                            opcionMenuRetorno = sc.nextInt();

                                            if (opcionMenuRetorno == 1) {
                                                System.out.println("Por favor, ingrese el monto para realizar el pago. $" + totalCompras);
                                            } else if (opcionMenuRetorno == 0) {
                                            break;                                               
                                            }
                                        }
                                    } while (opcionMenuRetorno != 0);                              
                                }
                                case 2 -> {
                                    do {
                                        if (!historialTipoDeEntrada.isEmpty()) {
                                            System.out.println("Has seleccionado Eliminar Entradas..." + saltoDeLinea + saltoDeLinea + "Escribe el numero de la entrada que deseas eliminar.");
                                            opcionEliminarEntrada = sc.nextInt();

                                            if (opcionEliminarEntrada >= 1 && opcionEliminarEntrada <= historialPrecioFinal.size()) {

                                                historialTipoDeEntrada.remove(opcionEliminarEntrada - 1);
                                                historialAsientos.remove(opcionEliminarEntrada - 1);
                                                historialFilas.remove(opcionEliminarEntrada - 1);
                                                historialTarifas.remove(opcionEliminarEntrada - 1);
                                                historialPrecios.remove(opcionEliminarEntrada - 1);
                                                historialPrecioFinal.remove(opcionEliminarEntrada - 1);
                                               
                                            } else {
                                                System.out.println("El numero de entrada no existe. Ingrese 0 para volver al Menu Principal.");
                                                opcionMenuRetorno = sc.nextInt();
                                                while (opcionMenuRetorno != 0) {
                                                    System.out.println("Invalido... Ingrese 0 para Continuar");
                                                    opcionMenuRetorno = sc.nextInt();
                                                    break;
                                                }
                                                break;
                                            }

                                            System.out.println("Eliminando entrada seleccionada: Nro" + opcionEliminarEntrada + saltoDeLinea + saltoDeLinea + "Desea seguir eliminando Entradas? Ingrese 1 para Si, 0 para volver al Carrito de Compras.");
                                            opcionMenuRetorno = sc.nextInt();
                                            while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                                System.out.println("Invalido... Ingrese 1 para seguir eliminando entradas, o 0 para volver al Menu Principal.");
                                                opcionMenuRetorno = sc.nextInt();
                                            }

                                        } else {
                                            System.out.println("El carrito esta vacio. No es posible eliminar entradas. Escriba 0 para volver al Menu Principal");
                                            opcionMenuRetorno = sc.nextInt();
                                            while (opcionMenuRetorno != 0) {
                                                System.out.println("Invalido... Ingrese 0 para volver al Menu Principal.");
                                                opcionMenuRetorno = sc.nextInt();
                                            }
                                        }
                                    } while (opcionMenuRetorno == 1);
                                }
                                case 3 -> {
                                    System.out.println("Volviendo al Menu Principal...");                                   
                                }
                                default -> {
                                    System.out.println("ERROR. Numero ingresado invalido. Ingrese 1 para volver a intentar, 0 para Volver al Menu Principal.");
                                    opcionMenuRetorno = sc.nextInt();
                                    while (opcionMenuRetorno < 0 || opcionMenuRetorno > 1) {
                                        System.out.println("Invalido... Ingrese 1 para volver a intentar, 0 para Volver al Menu Principal.");
                                        opcionMenuRetorno = sc.nextInt();
                                    }
                                }
                            }
                        } while (opcionMenuRetorno != 0);
                        System.out.println("Volviendo al Menu Principal..."+saltoDeLinea+ "---------oooooooooooooo---------");                        
                }                
                case 5 -> {
                    do {
                        System.out.println(saltoDeLinea + "                    ========= Asientos disponibles =========" + saltoDeLinea);

                        mostrarPlanoAsientos(filasVip, asientosVip, filaVip1, filaVip2, filaVip3);
                        mostrarPlanoAsientos(filasPlateaBaja, asientosPlateaBaja, filaPlateaBaja1, filaPlateaBaja2, filaPlateaBaja3);
                        mostrarPlanoAsientos(filasPlateaAlta, asientosPlateaAlta, filaPlateaAlta1, filaPlateaAlta2, filaPlateaAlta3);

                        System.out.println(saltoDeLinea + "Escriba 0 para volver al Menu Principal");
                        opcionMenuRetorno = sc.nextInt();

                        while (opcionMenuRetorno != 0) {
                            System.out.println("Invalido. Escriba 0 para volver al Menu Principal");
                            opcionMenuRetorno = sc.nextInt();
                        }
                    } while (opcionMenuRetorno != 0);
                    System.out.println("Volviendo al Menu Principal..."+saltoDeLinea+ "---------oooooooooooooo---------");
                }
                case 6 -> {
                    do {
                        System.out.println(saltoDeLinea + "===========================" + saltoDeLinea + "Has seleccionado 'Ver Promociones:'" + saltoDeLinea + saltoDeLinea + "- Descuento del 10% dirigido a estudiantes" + saltoDeLinea + "- Descuento del 15% dirigido a adultos mayores" + saltoDeLinea);

                        System.out.println(saltoDeLinea + "Escriba 0 para volver al Menu Principal)");
                        opcionMenuRetorno = sc.nextInt();

                        while (opcionMenuRetorno != 0) {
                            System.out.println("Invalido. Escriba 0 para volver al Menu Principal");
                            opcionMenuRetorno = sc.nextInt();
                        }
                    } while (opcionMenuRetorno != 0);
                    System.out.println("Volviendo al Menu Principal..."+saltoDeLinea+ "---------oooooooooooooo---------");                }
                case 7 -> {
                    System.out.println("Muchas gracias por por utilizar el sistema vitual de Teatro Moro. Vuelva pronto" + saltoDeLinea);
                    return;
                }
                default -> {
                    System.out.println("ERROR. Numero ingresado invalido. Ingrese 0 para volver a intentar.");
                    opcionMenuRetorno = sc.nextInt();
                    while (opcionMenuRetorno != 0) {
                        System.out.println("Invalido... Ingrese 0 para continuar");
                        opcionMenuRetorno = sc.nextInt();
                    }
                }
            }
        }
    }
}

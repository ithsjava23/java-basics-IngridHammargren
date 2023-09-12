package org.example;


import java.util.Locale;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Locale swedishLocale = Locale.of("sv","SE");
        Locale.setDefault(swedishLocale);

        Scanner scanner =new Scanner(System.in);
        boolean isRunning = true;
        int[]electricityPrice = new int[24];
        String[]intervalHour=new String[24];


        System.out.print("Välj ett alternativ i menyn");

        while (isRunning){

            System.out.print("Elpriser\n");
            System.out.print("========\n");
            System.out.print("1. Inmatning\n");
            System.out.print("2. Min, Max och Medel\n");
            System.out.print("3. Sortera\n");
            System.out.print("4. Bästa Laddningstid (4h)\n");
            System.out.print("e. Avsluta\n");

            String input = scanner.nextLine().toLowerCase();


            switch (input){
                case "1"-> inputElectricityPrice(scanner, electricityPrice, intervalHour);
                case "2"->minMaxMedElectricityPrice(electricityPrice,intervalHour);
                case "3"->sortPrice(electricityPrice, intervalHour);
                case "4"-> bestLoadingTime(electricityPrice, intervalHour);
                case "e"-> {
                    System.out.print("Programmet avslutas");
                    isRunning = false;

                }

                default -> System.out.println("Ogiltligt val, försök igen");


            }


        }
    }     public static void inputElectricityPrice(Scanner scanner,int[]arrElectricityPrice,String[]intervalHour){
        String startHour;
        String finalHour;


        for (int hour=0;hour<24; hour++){
            startHour = String.format("%02d",hour);
            finalHour = String.format("%02d",(hour+1)%25);

            intervalHour[hour] =startHour+"-"+finalHour;

            System.out.print(intervalHour[hour]+" ");
            arrElectricityPrice[hour] = scanner.nextInt();
            scanner.nextLine();
        }

    } public static void minMaxMedElectricityPrice(int[]electricityPrice,String[]intervalHour){
        int minprice = electricityPrice[0];
        int maxprice = electricityPrice[0];
        int totalprice = 0;
        String intervalMinprice=intervalHour[0];
        String intervalMaxprice=intervalHour[0];
        int price;


        for (int hour =0; hour<24; hour++){
            price = electricityPrice[hour];
            totalprice +=price; //

            if (price<minprice){
                minprice=price;
                intervalMinprice=intervalHour[hour];
            }
            if (price>maxprice){
                maxprice=price;
                intervalMaxprice=intervalHour[hour];
            }
        }

        System.out.print("Lägsta pris: "+intervalMinprice+", "+ minprice+" öre/kWh\n");
        System.out.print("Högsta pris: "+intervalMaxprice+", "+maxprice+" öre/kWh\n");
        System.out.printf("Medelpris: %.2f öre/kWh\n",(float) totalprice/24);




    } public static void sortPrice(int[]electricityPrice, String[]intervalHour){
        boolean changePlace;
        int tempPrice;
        String tempTid;

        for (int i=0;i< electricityPrice.length-1;i++){
            changePlace = false;
            for (int j=0; j<electricityPrice.length-i-1;j++){
                if (electricityPrice[j]<electricityPrice[j+1]){
                    tempPrice = electricityPrice[j];
                    electricityPrice[j]=electricityPrice[j+1];
                    electricityPrice[j+1]=tempPrice;

                    tempTid = intervalHour[j];
                    intervalHour[j]=intervalHour[j+1];
                    intervalHour [j+1] = tempTid;

                    changePlace = true;

                }
            } if (!changePlace){
                break;
            }
        } for (int i=0; i< electricityPrice.length;i++){
            System.out.print(intervalHour[i]+" "+ electricityPrice[i]+" öre\n");
        }


    } public static void bestLoadingTime(int[]electricityPrice, String[]intervalHour){
        float minTotalprice = Float.MAX_VALUE; //skapar en variabel och tilldelar det högsta möjliga värdet. Säkerställer att det första totalpriset som jag jämför med kommer vara mindre än värdet
        int startTime = 0;
        int totalpris;



        for (int i=0; i<=electricityPrice.length-4; i++ ){
            totalpris = 0;
            for (int j=i; j<i+4;j++){ //inre loop som går igenom de fyra timmarna
                totalpris+=electricityPrice[j];

            }
            if (totalpris<minTotalprice){//// Om det nuvarande intervallets totalpris är lägre än det tidigare bästa totalpriset, uppdatera variablerna
                minTotalprice=totalpris;
                startTime=i;


            }

        }
        String start = intervalHour[startTime].split("-")[0];
        System.out.print("Påbörja laddning klockan "+ start + "\n");
        System.out.printf("Medelpris 4h: %.1f öre/kWh\n",minTotalprice/4);

    }
}



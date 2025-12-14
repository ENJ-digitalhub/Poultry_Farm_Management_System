package PF;
import PF.SystemUtils;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;

public class Report{
    static LocalDateTime time = LocalDateTime.now();
    static SystemUtils tools = new SystemUtils();
    static Scanner read = new Scanner (System.in);
    static ArrayList<String> inventoryRecord = new ArrayList<>();
    static ArrayList<String> farmRecords = new ArrayList<>();

    public static void reportMenu(Runnable homeCallBack){
        System.out.println("\n"+"=".repeat(50)+"\n");
        System.out.println(tools.center("REPORT MENU",50));
        System.out.println("\n"+"=".repeat(50)+"\n");
        System.out.print("1. Daily Report ("+time.toLocalDate()+")\n2. Weekly Report (Last 7 Days)\n3. Monthly Report ("+time.getMonth()+" "+time.getYear()+")\n4. Summary Report\n5. Statistics Summary\n0. Back\nOption: ");
        int option=read.nextInt();
        if (option==0){
            tools.clearScreen();
            homeCallBack.run();
        }
        else{
            switch (option){
                case 1:
                    tools.clearScreen();
                    dailyReport(homeCallBack);
                    break;
                case 2:
                    tools.clearScreen();
                    weeklyReport(homeCallBack);
                    break;
                case 3:
                    tools.clearScreen();
                    monthlyReport(homeCallBack);
                    break;
                case 4:
                    tools.clearScreen();
                    reportSummary(homeCallBack);
                    break;
                case 5:
                    tools.clearScreen();
                    statsSummary(homeCallBack);
                    break;
                default:
                    tools.clearScreen();
                    System.out.println("Invalid selection");
                    reportMenu(homeCallBack);
                    break;
            }
        }
    }
    public static void dailyReport(Runnable homeCallBack){
        farmRecords = tools.reader(tools.FARMRECORD);

        String lastRecord = farmRecords.get(farmRecords.size()-1).replace("[","").replace("]","");
        String[] lastInRecord = lastRecord.split(",\\s");
        if (lastInRecord[0].equals(time.toLocalDate().toString())){
            System.out.println("--- Daily Report ("+lastInRecord[0]+") ---");
            System.out.println("\nEggs\t: "+lastInRecord[1]);
            System.out.println("Feed \t: "+lastInRecord[2]);
            System.out.println("Death\t: "+lastInRecord[3]);
            System.out.println("Comment\t: "+lastInRecord[4]);
        }
        else{
            System.out.println("No record found.");
        }

        System.out.println("\n0. Back");
		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==0){
            tools.clearScreen();
            reportMenu(homeCallBack);
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            dailyReport(homeCallBack);
        }
    }
    public static void weeklyReport(Runnable homeCallBack){
        farmRecords = tools.reader(tools.FARMRECORD);

        double totalEggs=0,totalFeeds=0,totalDeaths=0,avarageEggs;
        int count;

        for(count=1;count<=7;){
            if (farmRecords.size()<count){
                break;
            }
            String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
            String[] lastInRecord = lastRecord.split(",\\s");

            if (lastInRecord[0].equals(time.toLocalDate().minusDays(count-1).toString())){
                totalEggs+=Double.parseDouble(lastInRecord[1]);
                totalFeeds+=Double.parseDouble(lastInRecord[2]);
                totalDeaths+=Double.parseDouble(lastInRecord[3]);
            }
            count++;
        }
        avarageEggs=totalEggs/(count-1);

        System.out.println("--- Weekly Report (Last "+(count-1)+" Days) ---");
        System.out.println("\nTotal Eggs\t: "+totalEggs);
        System.out.println("Total Feeds\t: "+totalFeeds);
        System.out.println("Total Deaths\t: "+totalDeaths);
        System.out.println("Average Eggs/Days\t: "+String.format("%.2f",avarageEggs));
        System.out.print("\n0. Back\nOption: ");
        int option = read.nextInt();
        if (option==0){
            tools.clearScreen();
            reportMenu(homeCallBack);
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            weeklyReport(homeCallBack);
        }
    }
    public static void monthlyReport(Runnable homeCallBack){
        farmRecords = tools.reader(tools.FARMRECORD);

        String lowestEggDate="",highestEggDate="";
        double totalEggs=0,highestEgg=0,lowestEgg=0,totalFeeds=0,totalDeaths=0;
        int count=1;
        boolean done=true;

        while(done ){
            if (farmRecords.size()<count){
                break;
            }
            String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
            String[] lastInRecord = lastRecord.split(",\\s");

            if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){
                if (Double.parseDouble(lastInRecord[1])>highestEgg){
                    highestEgg=Double.parseDouble(lastInRecord[1]);
                    highestEggDate=lastInRecord[0];
                }
                if (count==1){
                    lowestEgg=Double.parseDouble(lastInRecord[1]);
                    lowestEggDate=lastInRecord[0];
                }
                if(Double.parseDouble(lastInRecord[1])<lowestEgg){
                    lowestEgg=Double.parseDouble(lastInRecord[1]);
                    lowestEggDate=lastInRecord[0];
                }

                totalEggs+=Double.parseDouble(lastInRecord[1]);
                totalFeeds+=Double.parseDouble(lastInRecord[2]);
                totalDeaths+=Double.parseDouble(lastInRecord[3]);
            }
            else{
                done = false;
            }
            count++;
        }

        System.out.println("--- Monthly Report ("+time.getMonth()+" "+time.getYear()+") ---");
        System.out.println("\nTotal Eggs\t: "+totalEggs);
        System.out.println("Total Feeds\t: "+totalFeeds);
        System.out.println("Total Deaths\t: "+totalDeaths);
        System.out.println("\nBest Production Day\t: "+highestEggDate+" ("+highestEgg+" eggs)");
        System.out.println("Worst Production Day\t: "+lowestEggDate+" ("+lowestEgg+" eggs)");
        System.out.println("\n0. Back");

		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==0){
            tools.clearScreen();
            reportMenu(homeCallBack);
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            monthlyReport(homeCallBack);
        }
    }
    public static void reportSummary(Runnable homeCallBack){
        farmRecords = tools.reader(tools.FARMRECORD);

        String today="";
        double todayEggs=0,thisWeekEgg=0,thisMonthEgg=0;
        int count=1;
        boolean done=true;

        while(done ){
            if (farmRecords.size()<count){
                break;
            }

            String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
            String[] lastInRecord = lastRecord.split(",\\s");

            if (lastInRecord[0].equals(time.toLocalDate().toString())){
                todayEggs=Double.parseDouble(lastInRecord[1]);
            }

            thisWeekEgg = 0.0;
            int i = 0;
            int counter = 1;
            while (i <7 && counter <= farmRecords.size()) {
                String lastRecordWeek = farmRecords.get(farmRecords.size() - counter).replace("[", "").replace("]", "");
                String[] lastInRecordWeek = lastRecordWeek.split(",\\s");

                if (lastInRecordWeek[0].equals(time.toLocalDate().minusDays(i).toString())) {
                    thisWeekEgg += Double.parseDouble(lastInRecordWeek[1]);
                }
                counter++;
                i++;
            }

            if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){
                thisMonthEgg+=Double.parseDouble(lastInRecord[1]);
            }
            else{
                done = false;
            }
            count++;
        }

        System.out.println("--- Summary Report ---");
        System.out.println("\nToday Egg\t: "+todayEggs);
        System.out.println("This Week\t: "+thisWeekEgg);
        System.out.println("This Month\t: "+thisMonthEgg);
        System.out.println("\n0. Back");

		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==0){
            tools.clearScreen();
            reportMenu(homeCallBack);
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            reportSummary(homeCallBack);
        }
    }
    public static void eggGraph(){
        farmRecords = tools.reader(tools.FARMRECORD);
        int count=1;

        System.out.println("Egg Production Graph(Last 7 Days)");
        System.out.println("\n"+"-".repeat(50)+"\n");

        while(count<=7){
            if (farmRecords.size()<count) break;

            String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
            String[] lastInRecord = lastRecord.split(",\\s");

            System.out.println(lastInRecord[0]+"\t|\t"+"*".repeat((int)Double.parseDouble(lastInRecord[1]))+" ("+(int)Double.parseDouble(lastInRecord[1])+")");
            count++;
        }
        System.out.println("\n"+"-".repeat(50)+"\n");
    }
    public static void statsSummary(Runnable homeCallBack){
        inventoryRecord = tools.reader(tools.INVENTORY);
        farmRecords = tools.reader(tools.FARMRECORD);

        String lowestEggDate="XXXX-XX-XX",highestEggDate="XXXX-XX-XX";
        double highestEgg=0,lowestEgg=0;
        int count=1;
        boolean done=true;

        while(done ){
            if (farmRecords.size()<count)break;
            String lastRecord = farmRecords.get(farmRecords.size()-count).replace("[","").replace("]","");
            String[] lastInRecord = lastRecord.split(",\\s");

            if (tools.monthsOfTheYear(lastInRecord[0]).equals(time.getMonth().toString())){
                double egg = Double.parseDouble(lastInRecord[1]);

                if (egg>highestEgg){
                    highestEgg=egg;
                    highestEggDate=lastInRecord[0];
                }
                if (count==1){
                    lowestEgg=egg;
                    lowestEggDate=lastInRecord[0];
                }
                if(egg<lowestEgg){
                    lowestEgg=egg;
                    lowestEggDate=lastInRecord[0];
                }
            }
            else{
                done = false;
            }
            count++;
        }

        System.out.println("--- Statistics Summary ---");
        System.out.println("\nHighest Eggs: \t: "+highestEgg+" eggs on "+highestEggDate);
        System.out.println("Lowest Eggs: \t: "+lowestEgg+" eggs on "+lowestEggDate);
        System.out.println("\n0. Back");

		System.out.print("Option: ");
        int option = read.nextInt();
        if (option==0){
            tools.clearScreen();
            reportMenu(homeCallBack);
        }
        else{
            tools.clearScreen();
            System.out.print("Invalid Input");
            monthlyReport(homeCallBack);
        }
    }
}

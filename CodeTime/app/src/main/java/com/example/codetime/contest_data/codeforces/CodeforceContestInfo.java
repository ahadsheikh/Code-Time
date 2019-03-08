package com.example.codetime.contest_data.codeforces;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CodeforceContestInfo {
    private Groups allContest;
    private Groups shortContest;
    private Groups longContest;
    private Groups finishedContest;
    private Groups liveContest;

    //Constructor
    public CodeforceContestInfo(String json){
        try {
            allContest = jsonToObject(json);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void calculateOthersContest(){

    }

    public Data getAllContest() {
        return allContest;
    }

    public void setAllContest(Groups allContest) {
        this.allContest = allContest;
    }

    public Data getShortContest() {
        return shortContest;
    }

    public void setShortContest(Groups shortContest) {
        this.shortContest = shortContest;
    }

    public Data getLongContest() {
        return longContest;
    }

    public void setLongContest(Groups longContest) {
        this.longContest = longContest;
    }

    public Data getFinishedContest() {
        return finishedContest;
    }

    public void setFinishedContest(Groups finishedContest) {
        this.finishedContest = finishedContest;
    }

    public Data getLiveContest() {
        return liveContest;
    }

    public void setLiveContest(Groups liveContest) {
        this.liveContest = liveContest;
    }

    public Data getUpComingContest() {
        return upComingContest;
    }

    public void setUpComingContest(Data upComingContest) {
        this.upComingContest = upComingContest;
    }

    private Data upComingContest;



    Groups jsonToObject(String json) throws FileNotFoundException {

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> type = new ArrayList<>();
        ArrayList<String> phase = new ArrayList<>();
        ArrayList<String> frozen = new ArrayList<>();
        ArrayList<Integer>durationSeconds = new ArrayList<>();
        ArrayList<Integer>startTimeSeconds = new ArrayList<>();
        ArrayList<Integer> relativeTimeSeconds = new ArrayList<>();


        Scanner in = new Scanner(new File("app/src/main/assets/codeforces/all_contest_data"));
        while(in.hasNext()){
            json = json + in.nextLine();
        }
        // Now do the magic.
        String str = json.split("\\[")[1];
        if(json.substring(0, 100).contains("OK")){
            Data.STATUS = true;
        }
        System.out.println(json.length());
        for(int i = 0; i < json.length()-30 ; i++) {

            if(json.substring(i, i+6).contains("\"name\"")){
                i = i + 6;
                String tmp = "";
                while(json.charAt(i) != '"'){
                    i++;
                }
                i++;
                while(json.charAt(i) != '"'){
                    tmp = tmp + Character.toString(json.charAt(i));
                    i++;
                }
                i++;
                name.add(tmp);
            }
            else if(json.substring(i, i+7).contains("\"phase\"")){
                String tmp = "";
                i = i+7;
                while(json.charAt(i) != '"'){
                    i++;
                }
                i++;
                while(json.charAt(i) != '"'){
                    tmp = tmp + Character.toString(json.charAt(i));
                    i++;
                }
                i++;
                if(tmp.equals("FINISHED")){
                    break;
                }
                phase.add(tmp);
            }
            else if(json.substring(i, i+17).contains("\"durationSeconds\"")){
                i = i+17;
                while(json.charAt(i) != ':'){
                    i++;
                }
                i++;
                String tmp = "";
                while(json.charAt(i) != ','){
                    tmp  = tmp + Character.toString(json.charAt(i));
                    i++;
                }
                i++;
                durationSeconds.add(Integer.parseInt(tmp.trim()));
            }
            else if(json.substring(i, i+18).contains("\"startTimeSeconds\"")){
                i = i+18;
                while(json.charAt(i) != ':'){
                    i++;
                }
                i++;
                String tmp = "";
                while(json.charAt(i) != ','){
                    tmp  = tmp + Character.toString(json.charAt(i));
                    i++;
                }
                i++;
                startTimeSeconds.add(Integer.parseInt(tmp.trim()));
            }
            else if(json.substring(i, i+21).contains("\"relativeTimeSeconds\"")){
                i = i+21;
                if(json.substring(i, i+19).contains("-")){
                    while(json.charAt(i) != '-'){
                        i++;
                    }
                    i++;
                    String tmp = "";
                    while(json.charAt(i) != '}'){
                        tmp  = tmp + Character.toString(json.charAt(i));
                        i++;
                    }
                    i++;
                    relativeTimeSeconds.add(Integer.parseInt(tmp.trim()));
                }
                else{
                    while(json.charAt(i) != ':'){
                        i++;
                    }
                    i++;
                    String tmp = "";
                    while(json.charAt(i) != '}'){
                        tmp  = tmp + Character.toString(json.charAt(i));
                        i++;
                    }
                    i++;
                    relativeTimeSeconds.add(Integer.parseInt(tmp.trim()));
                }
            }
        }

        Groups datas = new Groups();
        datas.name = new ArrayList<>(name);
        datas.phase = new ArrayList<>(phase);
        datas.durationSeconds = new ArrayList<>(durationSeconds);
        datas.startTimeSeconds = new ArrayList<>(startTimeSeconds);
        datas.relativeTimeSeconds = new ArrayList<>(relativeTimeSeconds);

        return datas;
    }
}
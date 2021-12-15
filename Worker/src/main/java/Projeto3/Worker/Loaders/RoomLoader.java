package Projeto3.Worker.Loaders;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

import com.opencsv.CSVReader;

import Projeto3.Worker.Models.Room;

public class RoomLoader {

    public static final LinkedList<Room> readRoomFile(final File file) {
        
        final LinkedList<Room> rooms = new LinkedList<>();
        try {
            //Creates the reader
            final InputStreamReader reader = new InputStreamReader(new FileInputStream(file),Charset.forName("UTF-8"));
            final CSVReader csvReader = new CSVReader(reader);
            
            //Saves the headers names for the correct allocation of values to variables
            String[] headers = csvReader.readNext(); 
            for(String header : headers) {
            	System.out.println("Header: " + header);
            }
            int[] order_headers = getOrder(headers);
            
            //Will contain a row of the csv
            String[] tokens;

            while ((tokens = csvReader.readNext()) != null) {
                rooms.add(creationRooms(tokens, order_headers));
            }
            csvReader.close();
        } catch (Exception  e) {
            e.printStackTrace();
        }
        return rooms;
    }
    
    private static Room creationRooms(String[] tokens, int[] order_headers){
        
        //Checks which characteristics the room has
        LinkedList<Boolean> characteristics = new LinkedList<>();
        for (int index = 0; index < order_headers.length; index++) {
        	System.out.println("Dentro do for" + tokens[order_headers[index]]);
			if (tokens[order_headers[index]].equals("X") || tokens[order_headers[index]].equals("x")) {
				//System.out.println("Era um X");
				characteristics.add(true);
			} else {
				characteristics.add(false);
			}
		}
        System.out.println("tokens[order_headers[0]]:" + tokens[order_headers[0]] + " " +   "tokens[order_headers[1]]:" + tokens[order_headers[1]] +
        		" " +"tokens[order_headers[2]]:" +tokens[order_headers[2]] + " " +"tokens[order_headers[3]]:" +tokens[order_headers[3]] +" " +  
        		"tokens[order_headers[4]]:" +tokens[order_headers[4]]);
        Room r = new Room(tokens[order_headers[0]], tokens[order_headers[1]], Integer.parseInt(tokens[order_headers[2]]), Integer.parseInt(tokens[order_headers[3]]), 
        Integer.parseInt(tokens[order_headers[4]]), characteristics);
        return r;
    } 
    
    // Due to json files coming with the columns of the csv in different order
    private static int[] getOrder(String[] headers){
        String[] order = Room.HEADERS;
        int[] indexes = new int[order.length];               
        int count1 = 0;
        for(String label : headers){
        	System.out.println("Label: " + label);
            int count2 = 0;
                for(String label2 : order){
                	System.out.println("Label 2: " + label2);
                	System.out.println(label2.equals(﻿new String("Edifício")));
                    if(label2.equals(label)){
                    	System.out.println("Labels iguais: " + label + " " + label2);
                        indexes[count1] = count2;
                        break;
                    }
                    count2++;
                }
            count1++;
        }
        return indexes;
    }

}
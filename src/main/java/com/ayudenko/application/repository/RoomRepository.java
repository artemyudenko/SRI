package com.ayudenko.application.repository;

import com.ayudenko.soap.Room;
import com.ayudenko.soap.Type;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class RoomRepository {

    private final Set<Room> rooms = new HashSet<>();

    @PostConstruct
    private void init() {
        generateRooms(1000, Type.SINGLE, 10);
        generateRooms(2000, Type.DOUBLE, 20);
        generateRooms(3000, Type.FOUR, 30);
    }

    private void generateRooms(int cost, Type type, int number) {
        for (int i = 1; i < 6; i++) {
            Room room = new Room();
            room.setCost(cost);
            room.setType(type);
            room.setNumber(number * i);
            rooms.add(room);
        }
    }

    public Set<Room> getRoomsByType(Type type) {
        Set<Room> result = new HashSet<>();
        for (Room room : rooms) {
            if (room.getType() == type) {
                result.add(room);
            }
        }

        return result;
    }

}

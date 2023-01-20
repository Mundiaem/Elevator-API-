package com.building.elevator.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long movement_id;
    @Column(name = "destination")
    private int destination;
    @Column(name = "from_floor")
    private int from_floor;
    @ManyToOne
    @JoinColumn(name = "movement_elevator_id")
    private Elevator elevator;
}

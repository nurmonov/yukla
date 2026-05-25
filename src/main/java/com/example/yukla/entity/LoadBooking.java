package com.example.yukla.entity;

import com.example.yukla.entity.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "load_bookings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoadBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "load_id", nullable = false)
    private Load load;

    @ManyToOne
    @JoinColumn(name = "driver_id", nullable = false)
    private User driver;

    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.PENDING;

    private Integer agreedPrice;

    private LocalDateTime bookedAt;
    private LocalDateTime deliveredAt;
}

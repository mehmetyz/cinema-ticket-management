package com.sqlcinema.backend.model.response;

import com.sqlcinema.backend.model.Ticket;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class TicketResponse extends Ticket {
    private String title;
    private String name;
}

package com.neo.DatabaseModel.Message;

import com.neo.DatabaseModel.Ticket;
import com.neo.DatabaseModel.Users.User;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value = MessageBase.COMPLAINT+"")
public class Complaint extends MessageBase {

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Ticket ticket;

	public Complaint() {
	}

    public Complaint(String message, User sender, Ticket ticket) {
        super(message, sender);
        this.ticket = ticket;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

}

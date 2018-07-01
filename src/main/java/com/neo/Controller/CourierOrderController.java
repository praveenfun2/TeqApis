package com.neo.Controller;

import com.neo.Model.RStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.neo.DatabaseModel.Order.CourierOrder.*;

@RestController
@RequestMapping("/order/courier")
public class CourierOrderController {

    public static final String STATUS_RECEIVED = "received", STATUS_PICKED_UP = "picked up", STATUS_DISPATCHED = "out for delivery",
            STATUS_DELIVERED = "Delivered", STATUS_CANCELED = "Cancelled";

    @RequestMapping("/status/map")
    public List<RStatus> getStatusStrings() {
        List<RStatus> statuses = new ArrayList<>();

        statuses.add(new RStatus(STATUS_RECEIVED_KEY, STATUS_RECEIVED));
        statuses.add(new RStatus(STATUS_PICKED_UP_KEY, STATUS_PICKED_UP));
        statuses.add(new RStatus(STATUS_DISPATCHED_KEY, STATUS_DISPATCHED));
        statuses.add(new RStatus(STATUS_DELIVERED_KEY, STATUS_DELIVERED));

        return statuses;
    }

    public static String getStatus(int value) {
        String status = null;

        switch (value) {
            case STATUS_RECEIVED_KEY:
                status = STATUS_RECEIVED;
                break;
            case STATUS_CANCELED_KEY:
                status = STATUS_CANCELED;
                break;
            case STATUS_PICKED_UP_KEY:
                status = STATUS_PICKED_UP;
                break;
            case STATUS_DISPATCHED_KEY:
                status = STATUS_DISPATCHED;
                break;
            case STATUS_DELIVERED_KEY:
                status = STATUS_DELIVERED;
                break;
        }

        return status;
    }

}
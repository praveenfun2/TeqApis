package com.neo.Controller;

import com.neo.DatabaseModel.Order.CourierOrder;
import com.neo.Model.RStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.neo.DatabaseModel.Order.CardOrder.*;

@RestController
@RequestMapping("/order/card")
public class CardOrderController {

    public static final String STATUS_PRINTED = "printed", STATUS_RECEIVED = "received",
            STATUS_PROGRESS = "In progress", STATUS_CANCELED = "canceled", STATUS_DISPATCHED = "dispatched";

    @RequestMapping("/status/map")
    public List<RStatus> getStatusStrings() {
        List<RStatus> statuses = new ArrayList<>();

        statuses.add(new RStatus(STATUS_RECEIVED_KEY, STATUS_RECEIVED));
        statuses.add(new RStatus(STATUS_PROGRESS_KEY, STATUS_PROGRESS));
        statuses.add(new RStatus(STATUS_PRINTED_KEY, STATUS_PRINTED));

        return statuses;
    }

    public static String getStatus(int value) {
        String status = null;

        switch (value) {
            case STATUS_RECEIVED_KEY:
                status = STATUS_RECEIVED;
                break;
            case STATUS_PROGRESS_KEY:
                status = STATUS_PROGRESS;
                break;
            case CourierOrder.STATUS_RECEIVED_KEY:
            case STATUS_PRINTED_KEY:
                status = STATUS_PRINTED;
                break;
            case STATUS_CANCELED_KEY:
                status = STATUS_CANCELED;
                break;
            case CourierOrder.STATUS_PICKED_UP_KEY:
                status = STATUS_DISPATCHED;
                break;
            case CourierOrder.STATUS_DISPATCHED_KEY:
                status = CourierOrderController.STATUS_DISPATCHED;
                break;
            case CourierOrder.STATUS_DELIVERED_KEY:
                status = CourierOrderController.STATUS_DELIVERED;
                break;
        }

        return status;
    }

}

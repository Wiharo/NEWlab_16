package com.company.cafe.order;

import com.company.cafe.interfaces.Order;


public class QueueNode {

    private QueueNode next;
    private QueueNode prev;
    private Order value;
}
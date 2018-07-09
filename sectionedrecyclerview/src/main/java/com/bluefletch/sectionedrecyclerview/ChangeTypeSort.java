package com.bluefletch.sectionedrecyclerview;

import java.util.Comparator;

/**
 * Created by robertgross on 12/3/17.
 */

public class ChangeTypeSort implements Comparator<Change> {
    @Override
    public int compare(Change o1, Change o2) {
        if(o1.getType() == ChangeType.MOVE && o2.getType() != ChangeType.MOVE) {
            return -1;
        }

        if(o1.getType() == ChangeType.INSERT && o2.getType() != ChangeType.MOVE) {
            return -1;
        } else if(o1.getType() == ChangeType.INSERT && o2.getType() == ChangeType.MOVE) {
            return 1;
        }

        if (o1.getType() == ChangeType.CHANGE && o2.getType() == ChangeType.MOVE) {
            return 1;
        } else if(o1.getType() == ChangeType.CHANGE && o2.getType() == ChangeType.INSERT) {
            return 1;
        }  else if(o1.getType() == ChangeType.CHANGE && o2.getType() != ChangeType.REMOVE) {
            return -1;
        }

        if(o1.getType() == ChangeType.REMOVE && o2.getType() != ChangeType.REMOVE) {
            return 1;
        } else {
            return 0;
        }
    }
}

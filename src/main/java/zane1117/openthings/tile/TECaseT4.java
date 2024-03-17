package zane1117.openthings.tile;

import li.cil.oc.common.InventorySlots;
import li.cil.oc.common.container.ComponentSlot;
import li.cil.oc.common.tileentity.Case;
import li.cil.oc.util.Color;

public class TECaseT4 extends Case {
    public TECaseT4() {
        super();
        this.tier_$eq(4); // I hate Scala.
        this.setColor(0x339911); // Should be green, hopefully
    }

}

/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/06/22
 * purpose: M3-Summative - Vending Machine
 */
package com.sg.vendingmachine;

import com.sg.vendingmachine.controller.VendingMachineController;
import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoImpl;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sg.vendingmachine.ui.UserIO;
import com.sg.vendingmachine.ui.UserIOConsoleImpl;
import com.sg.vendingmachine.ui.VendingMachineView;

public class App {

    public static void main(String[] args) {

        UserIO myIO = new UserIOConsoleImpl();
        VendingMachineDao myDao = new VendingMachineDaoImpl();
        VendingMachineView myView = new VendingMachineView(myIO);
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(myDao, auditDao);
        VendingMachineController controller = new VendingMachineController(myView, service);

        controller.run();

    }

}

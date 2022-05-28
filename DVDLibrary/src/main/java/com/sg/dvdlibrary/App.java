/**
 * @author Dillian Rhoades
 * email: dr304113@ohio.edu
 * date: 02/02/21
 * purpose: M3 Assignment "DVD Library-Adding Lambdas and Streams"
 */
package com.sg.dvdlibrary;

import com.sg.dvdlibrary.controller.DVDLibraryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
//        UserIO myIo = new UserIOConsoleImpl();
//        DVDLibraryView myView = new DVDLibraryView(myIo);
//        DVDLibraryDao myDao = new DVDLibraryDaoFileImpl();
//        DVDLibraryLambdasAndStreamsDao LSDao = new DVDLibraryDaoFileImpl();
//        DVDLibraryController controller = new DVDLibraryController(myDao, myView, LSDao);
//
//        controller.run();

        ApplicationContext ctx
                = new ClassPathXmlApplicationContext("applicationContext.xml");
        DVDLibraryController controller
                = ctx.getBean("controller", DVDLibraryController.class);
        controller.run();
    }

}

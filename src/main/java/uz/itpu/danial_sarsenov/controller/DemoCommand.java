package uz.itpu.danial_sarsenov.controller;

import java.util.List;
import java.util.ArrayList;

public class DemoCommand implements Command {

    @Override
    public Response execute(String[] args) {
        System.out.println("🚀 Welcome to the Demo Mode!");
        System.out.println("We'll show you some example commands step by step.\n");

        Controller controller = ControllerFactory.getInstance();

        List<String> demoSteps = new ArrayList<>();
        demoSteps.add("find all");
        demoSteps.add("find kitchen");
        demoSteps.add("find cooling");
        demoSteps.add("find electric");
        demoSteps.add("find all price=50;200");

        for (String step : demoSteps) {
            System.out.println("> " + step);


            Request request = RequestFactory.of(step);


            Response response = controller.execute(request);

            System.out.println(response.getMessage());
        }

        System.out.println("\n✨ Demo finished! You can now try commands on your own.");


        return new DemoResponse("", false);
    }
}

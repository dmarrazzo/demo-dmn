package dmn;

import java.util.Set;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNDecisionResult;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.api.core.ast.InputDataNode;

import model.Person;

public class DMNRunner {

	public static void main(String[] args) {
			KieServices kieServices = KieServices.Factory.get();
	
			KieContainer kieContainer = kieServices.getKieClasspathContainer();
			DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime( DMNRuntime.class );
			DMNResult dmnResult = testDrivingEligibility(dmnRuntime);
	
			for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
				System.out.println("Decision '" + dr.getDecisionName() + "' : " + dr.getResult());
			}
	}

	public static DMNResult testDrivingEligibility(DMNRuntime dmnRuntime) {
		DMNModel dmnModel = dmnRuntime.getModel("http://www.trisotech.com/definitions/_acfd1ef7-d9d6-49e5-b805-2c4423048a16", "driving-eligibility-2");
		
		// <<< optional check
		Set<InputDataNode> inputs = dmnModel.getInputs();
		for (InputDataNode inputDataNode : inputs) {
			System.out.println(inputDataNode.getType());
		}
		//  optional check >>>
		
		DMNContext dmnContext = dmnRuntime.newContext();
		
		Person person = new Person("Tony", 19, "italy");
		dmnContext.set("Person", person);

		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		return dmnResult;
	}

	public static DMNResult testJavaInvocation(DMNRuntime dmnRuntime) {
		DMNModel dmnModel = dmnRuntime.getModel("http://www.trisotech.com/definitions/_392573d3-b0fb-4118-b40e-b8da2aba9c23", "JavaInvocation");
		
		// <<< optional check
		Set<InputDataNode> inputs = dmnModel.getInputs();
		for (InputDataNode inputDataNode : inputs) {
			System.out.println(inputDataNode.getType());
		}
		//  optional check >>>
		
		DMNContext dmnContext = dmnRuntime.newContext();
		
		dmnContext.set("Input", "input");

		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		return dmnResult;
	}

}

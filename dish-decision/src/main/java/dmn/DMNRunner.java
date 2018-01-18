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

public class DMNRunner {

	public static void main(String[] args) {
			KieServices kieServices = KieServices.Factory.get();
	
			KieContainer kieContainer = kieServices.getKieClasspathContainer();
			DMNRuntime dmnRuntime = kieContainer.newKieSession().getKieRuntime( DMNRuntime.class );
			DMNModel dmnModel = dmnRuntime.getModel("http://www.trisotech.com/definitions/_15587d56-cedf-4cef-9f16-fe20b223021b", "dish-decision");
			
			// <<< optional check
			Set<InputDataNode> inputs = dmnModel.getInputs();
			for (InputDataNode inputDataNode : inputs) {
				System.out.println(inputDataNode.getType());
			}
			//  optional check >>>
			
			DMNContext dmnContext = dmnRuntime.newContext();
			
			dmnContext.set("Season", "Fall");
			dmnContext.set("How many guests", 10);
	
			DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
	
			for (DMNDecisionResult dr : dmnResult.getDecisionResults()) {
				System.out.println("Decision '" + dr.getDecisionName() + "' : " + dr.getResult());
			}
	}

}

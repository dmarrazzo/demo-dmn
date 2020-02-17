package dmn;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class InputDepTest {
	private DMNRuntime dmnRuntime;
	private DMNModel dmnModel;
	private DMNContext dmnContext;

	@Before
	public void before() {
		KieServices kieServices = KieServices.Factory.get();

		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		dmnRuntime = kieContainer.newKieSession().getKieRuntime( DMNRuntime.class );
		dmnModel = dmnRuntime.getModel("http://www.trisotech.com/dmn/definitions/_0e21f378-8331-477f-b1b5-9a616404a3bd", "input dep");
		
		dmnContext = dmnRuntime.newContext();		
	}

	
	@Test
	public void testNotUS() {		
		
		dmnContext.set("in2", "bbb");

		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		System.out.println(dmnResult);
		System.out.println("decision: "+ dmnResult.getDecisionResults().size());
		dmnResult.getDecisionResults().forEach(r -> {
			System.out.println(r.getEvaluationStatus());
		});
	
	}

		
}

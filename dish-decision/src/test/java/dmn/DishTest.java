package dmn;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;

public class DishTest {
	private DMNRuntime dmnRuntime;
	private DMNModel dmnModel;
	private DMNContext dmnContext;

	@Before
	public void before() {
		KieServices kieServices = KieServices.Factory.get();

		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		dmnRuntime = kieContainer.newKieSession().getKieRuntime( DMNRuntime.class );
		dmnModel = dmnRuntime.getModel("http://www.trisotech.com/definitions/_15587d56-cedf-4cef-9f16-fe20b223021b", "dish-decision");
		
		dmnContext = dmnRuntime.newContext();		
	}

	
	@Test
	public void testStew() {		
		dmnContext.set("Season", "Fall");
		dmnContext.set("How many guests", 10);

		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		Assert.assertEquals(dmnResult.getDecisionResults().size(), 1);
		Assert.assertEquals(dmnResult.getDecisionResults().get(0).getResult(), "Stew");
	}

	@Test
	public void testLightSalad() {
		dmnContext.set("Season", "Summer");
		dmnContext.set("How many guests", 1);

		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		Assert.assertEquals(dmnResult.getDecisionResults().size(), 1);
		Assert.assertEquals(dmnResult.getDecisionResults().get(0).getResult(), "Light Salad and a nice Steak");
	}
	
}

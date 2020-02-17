package dmn;

import static org.kie.dmn.core.util.DynamicTypeUtils.entry;
import static org.kie.dmn.core.util.DynamicTypeUtils.prototype;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.dmn.api.core.DMNContext;
import org.kie.dmn.api.core.DMNModel;
import org.kie.dmn.api.core.DMNResult;
import org.kie.dmn.api.core.DMNRuntime;
import org.kie.dmn.api.core.event.AfterEvaluateBKMEvent;
import org.kie.dmn.api.core.event.DMNRuntimeEventListener;

public class SOSTest {
	private final String NAMESPACE = "http://www.trisotech.com/dmn/definitions/_a557bfac-f9f7-41b9-a733-070b3d8f1d38";

	private DMNRuntime dmnRuntime;
	private DMNModel dmnModel;
	private DMNContext dmnContext;

	@Before
	public void before() {
		KieServices kieServices = KieServices.Factory.get();

		KieContainer kieContainer = kieServices.getKieClasspathContainer();
		dmnRuntime = kieContainer.newKieSession().getKieRuntime(DMNRuntime.class);
		dmnModel = dmnRuntime.getModel(NAMESPACE, "SOS");
		
		dmnRuntime.addListener( new DMNRuntimeEventListener() {
			@Override
			public void afterEvaluateBKM(AfterEvaluateBKMEvent event) {
				System.out.println(">>> " + event.getResult().getContext());
			}
		} );
		

		dmnContext = dmnRuntime.newContext();
	}

	@Test
	public void test() {

        dmnContext.set("HealthFact", prototype(entry("id", "blood-pressure"),
                                            entry("measure", Arrays.asList(prototype(entry("id", "systolic"),
                                                                                     entry("value", 180)),
                                                                           prototype(entry("id", "diastolic"),
                                                                                     entry("value", 90))))));

//		DMNResult dmnResult = dmnRuntime.evaluateAll(dmnModel, dmnContext);
		DMNResult dmnResult = dmnRuntime.evaluateByName(dmnModel, dmnContext, "Pressure DT");

		System.out.println(dmnResult);
		System.out.println("decision: " + dmnResult.getDecisionResults().size());
		dmnResult.getDecisionResults().forEach(r -> {
			System.out.println(r.getEvaluationStatus());
		});

		dmnResult.getMessages().forEach(m -> {
			System.out.println("message: " + m.getMessage());
		});

	}

}

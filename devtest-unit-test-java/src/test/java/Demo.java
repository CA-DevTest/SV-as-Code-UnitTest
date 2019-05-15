
import org.aeonbits.owner.ConfigFactory;
import org.junit.Test;

import com.ca.devtest.sv.devtools.SVasCodeConfig;

public class Demo {

	@Test
	public void test() {
		SVasCodeConfig cfg = ConfigFactory
			    .create(SVasCodeConfig.class,System.getenv());
		System.out.println(cfg.login());
	}

}

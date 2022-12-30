package fpt.lhlvb.softskillcommunity;

import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.ExcludePackages;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@Suite
@SelectPackages("fpt.lhlvb.softskillcommunity")
@ExcludePackages("fpt.lhlvb.softskillcommunity.Common")
class SoftSkillCommunityApplicationTests {
}

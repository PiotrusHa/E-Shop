package piotrusha.e_shop.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

public class BaseControllerTest extends BaseTestWithDatabase {

    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper jsonObjectMapper;

    protected void init(Object controller) {
        super.init();
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                                 .build();
    }

    protected <T> T readValueAsObject(MvcResult mvcResult, Class<T> type) throws Exception {
        return jsonObjectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), type);
    }

    protected <T> List<T> readValueAsList(MvcResult mvcResult, Class<T> type) throws Exception {
        return jsonObjectMapper.readValue(mvcResult.getResponse().getContentAsByteArray(), collectionType(type));
    }

    private CollectionType collectionType(Class type) {
        return jsonObjectMapper.getTypeFactory()
                               .constructCollectionType(List.class, type);
    }

    protected String writeValueAsJson(Object object) throws JsonProcessingException {
        return jsonObjectMapper.writeValueAsString(object);
    }

}

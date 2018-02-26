//package com.squidsquads.unit.service.profile;
//
//import com.squidsquads.Application;
//import com.squidsquads.form.userProfile.request.CreateModifyRequest;
//import com.squidsquads.service.userProfile.UserProfileService;
//import com.squidsquads.unit.service.AbstractPubAdminTest;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.web.WebAppConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.context.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestContext.class, Application.class})
//@WebAppConfiguration
//public class UserProfileTest extends AbstractPubAdminTest {
//
//
//    @Autowired
//    private UserProfileService userProfileService;
//
//    private MockMvc mockMvc;
//
//
//    /* *********** Create a profile *********** */
//
//    @Test
//    public void createProfile(){
//
//        CreateModifyRequest request = new CreateModifyRequest();
//        request.setName("Test");
//        request.setDescription("Some description");
//        String[] urls = new String[]{"google.com", "etsmtl.ca"};
//        request.setUrls(urls);
//
////        when(userProfileService.userProfileRepository.findByNameAndAccountID(request.getName(), super.getA))
//
////        when(todoServiceMock.findAll()).thenReturn(Arrays.asList(first, second));
//
////        mockMvc.perform(post("/todo"))
////                .andExpect(status().isOk())
////                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
////                .andExpect(jsonPath("$", hasSize(2)))
////                .andExpect(jsonPath("$[0].id", is(1)))
////                .andExpect(jsonPath("$[0].description", is("Lorem ipsum")))
////                .andExpect(jsonPath("$[0].title", is("Foo")))
////                .andExpect(jsonPath("$[1].id", is(2)))
////                .andExpect(jsonPath("$[1].description", is("Lorem ipsum")))
////                .andExpect(jsonPath("$[1].title", is("Bar")));
////
////        verify(todoServiceMock, times(1)).findAll();
////        verifyNoMoreInteractions(todoServiceMock);
//    }
//
//}

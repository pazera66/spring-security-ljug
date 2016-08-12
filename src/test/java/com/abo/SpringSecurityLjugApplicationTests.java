package com.abo;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.halLinks;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringSecurityLjugApplication.class)
@WebAppConfiguration
public class SpringSecurityLjugApplicationTests {

	@Rule
	public final RestDocumentation restDoc= new RestDocumentation("target/generater-snippets");

	@Autowired
	private WebApplicationContext context;
	private MockMvc mockMvc;

	@Before
	public void init(){
		mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(documentationConfiguration(this.restDoc)).build();
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void booksExist() throws Exception {


		this.mockMvc.perform(get("/books/mybooks").accept(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[3].title").value("Test_Book"))
                .andExpect(jsonPath("$[3].id").value(4))
                .andExpect(jsonPath("$[0].author").value("J.AM"));
//                .andDo(document("book-exists", preprocessResponse(prettyPrint()),
//                        links(
//                                linkWithRel("self").description("List of books owned by the logged user")
//                        ),
//                        responseFields(
//                                fieldWithPath("id").description("ID of the book"),
//                                fieldWithPath("title").description("Title of this book"),
//                                fieldWithPath("author").description("Author of this book"),
//                                fieldWithPath("owner").description("Owner of this book")
//
//                        )
//                        ));
	}

    @Test
    //@Ignore
    public void bookExists() throws Exception {


        this.mockMvc.perform(get("/books/3").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.title").value("Game of Thrones"))
                .andDo(document("book-exists", preprocessResponse(prettyPrint()),
                        links(
                                 linkWithRel("self").description("Check if book exists")
                        ),
                        responseFields(
                                fieldWithPath("id").description("ID of the book"),
                                fieldWithPath("title").description("Title of this book"),
                                fieldWithPath("author").description("Author of this book"),
                                fieldWithPath("owner").description("Owner of this book")
                        )
                        ));
    }

    @Test
    public void bookDoesntExist() throws Exception {


        this.mockMvc.perform(get("/books/mybooks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[4].title").doesNotExist())
                .andDo(document("book-dont-exist"));
    }




}

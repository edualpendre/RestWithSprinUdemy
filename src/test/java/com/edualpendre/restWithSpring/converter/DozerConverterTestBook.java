package com.edualpendre.restWithSpring.converter;

import com.edualpendre.restWithSpring.converter.mocks.MockBook;
import com.edualpendre.restWithSpring.data.model.Book;
import com.edualpendre.restWithSpring.data.vo.v1.BookVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DozerConverterTestBook {
	
    MockBook inputObject;

    @Before
    public void setUp() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTest() {
        BookVO output = DozerConverter.parseObject(inputObject.mockEntity(), BookVO.class);
        Assert.assertEquals(Long.valueOf(0L), output.getKey());
        Assert.assertEquals("Author Test0", output.getAuthor());
        Assert.assertEquals("Title Test0", output.getTitle());
        Assert.assertEquals(Double.valueOf(0.0), output.getPrice());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<BookVO> outputList = DozerConverter.parseListObjects(inputObject.mockEntityList(), BookVO.class);
        BookVO outputZero = outputList.get(0);

        Assert.assertEquals(Long.valueOf(0L), outputZero.getKey());
        Assert.assertEquals("Author Test0", outputZero.getAuthor());
        Assert.assertEquals("Title Test0", outputZero.getTitle());
        Assert.assertEquals(Double.valueOf(0.0), outputZero.getPrice());

        BookVO outputSeven = outputList.get(7);

        Assert.assertEquals(Long.valueOf(7L), outputSeven.getKey());
        Assert.assertEquals("Author Test7", outputSeven.getAuthor());
        Assert.assertEquals("Title Test7", outputSeven.getTitle());
        Assert.assertEquals(Double.valueOf(7.0), outputSeven.getPrice());

        BookVO outputTwelve = outputList.get(12);

        Assert.assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        Assert.assertEquals("Author Test12", outputTwelve.getAuthor());
        Assert.assertEquals("Title Test12", outputTwelve.getTitle());
        Assert.assertEquals(Double.valueOf(12.0), outputTwelve.getPrice());
    }

    @Test
    public void parseVOToEntityTest() {
        Book output = DozerConverter.parseObject(inputObject.mockVO(), Book.class);
        Assert.assertEquals(Long.valueOf(0L), output.getId());
        Assert.assertEquals("Author Test0", output.getAuthor());
        Assert.assertEquals("Title Test0", output.getTitle());
        Assert.assertEquals(Double.valueOf(0.0), output.getPrice());
    }

    @Test
    public void parserVOListToEntityListTest() {
        List<Book> outputList = DozerConverter.parseListObjects(inputObject.mockVOList(), Book.class);
        Book outputZero = outputList.get(0);

        Assert.assertEquals(Long.valueOf(0L), outputZero.getId());
        Assert.assertEquals("Author Test0", outputZero.getAuthor());
        Assert.assertEquals("Title Test0", outputZero.getTitle());
        Assert.assertEquals(Double.valueOf(0.0), outputZero.getPrice());

        Book outputSeven = outputList.get(7);

        Assert.assertEquals(Long.valueOf(7L), outputSeven.getId());
        Assert.assertEquals("Author Test7", outputSeven.getAuthor());
        Assert.assertEquals("Title Test7", outputSeven.getTitle());
        Assert.assertEquals(Double.valueOf(7.0), outputSeven.getPrice());

        Book outputTwelve = outputList.get(12);

        Assert.assertEquals(Long.valueOf(12L), outputTwelve.getId());
        Assert.assertEquals("Author Test12", outputTwelve.getAuthor());
        Assert.assertEquals("Title Test12", outputTwelve.getTitle());
        Assert.assertEquals(Double.valueOf(12.0), outputTwelve.getPrice());
    }
}

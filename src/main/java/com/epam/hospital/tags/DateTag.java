package com.epam.hospital.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.time.LocalDate;

public class DateTag extends SimpleTagSupport {

    @Override
    public void doTag() throws IOException {
        LocalDate currentDate = LocalDate.now();
        JspWriter writer = getJspContext().getOut();
        writer.print(currentDate);
    }
}

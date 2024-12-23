package sisibibi.wanttogram.common;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

public class AuthFilter implements Filter {
    private static final String[] WHITE_LIST = {"/", "/auth/register", "/auth/login", "/feeds*", "/members*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        String method = httpRequest.getMethod();
        if (!isWhiteList(requestURI, method)) {
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("userEmail") == null) {
                httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"error\": \"Log in is required.\"}");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI, String method) {
        Boolean isScheduleNonGetRequest = requestURI.startsWith("/feeds") && !"GET".equals(method);
        Boolean isCommentNonGetRequest = requestURI.startsWith("/members") && !"GET".equals(method);

        if (isScheduleNonGetRequest || isCommentNonGetRequest) {
            return false;
        }
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}

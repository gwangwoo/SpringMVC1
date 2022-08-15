package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.ControllerV2;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@WebServlet(name = "frontControllerServletV3" , urlPatterns = "/front-controller/v3/*")
public class FrontControllerServiceV3 extends HttpServlet {

  private Map<String, ControllerV3> controllerMap  = new HashMap<>();

  public FrontControllerServiceV3() {
    controllerMap.put("/front-controller/v3/members/new-form" , new MemberFormControllerV3());
    controllerMap.put("/front-controller/v3/members/save" , new MemberSaveControllerV3());
    controllerMap.put("/front-controller/v3/members" , new MemberListControllerV3());
  }

  @Override
  protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    log.info("frontControllerServletV2");
    String requestURI = request.getRequestURI();
    ControllerV3 controller = controllerMap.get(requestURI);
    if(controller == null) {
      response.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    Map<String, String> paramMap = createParamMap(request);
    ModelView modelView = controller.process(paramMap);

    String viewName = modelView.getViewName(); // 논리이름 new-form
    MyView myView = viewResolver(viewName);
    myView.render(modelView.getModel(), request, response);

  }

  private MyView viewResolver(String viewName) {
    return new MyView("/WEB-INF/views/" + viewName + ".jsp");
  }

  private Map<String, String> createParamMap(HttpServletRequest request) {
    Map<String, String> paramMap = new HashMap<>();
    request.getParameterNames().asIterator().forEachRemaining(param -> paramMap.put(param, request.getParameter(param)));
    return paramMap;
  }
}
package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import hello.servlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
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
@WebServlet(name = "frontControllerServletV1" , urlPatterns = "/front-controller/v1/*")
public class FrontControllerServiceV1 extends HttpServlet {

  private Map<String, ControllerV1> controllerMap  = new HashMap<>();

  public FrontControllerServiceV1() {
    controllerMap.put("/front-controller/v1/members/new-form" , new MemberFormControllerV1());
    controllerMap.put("/front-controller/v1/members/save" , new MemberSaveControllerV1());
    controllerMap.put("/front-controller/v1/members" , new MemberListControllerV1());
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    log.info("frontControllerServletV1");
    String requestURI = req.getRequestURI();
    ControllerV1 controller = controllerMap.get(requestURI);
    if(controller == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }
    controller.process(req,resp);
  }
}

package org.jetbrains.teamcity.invitations;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class AbstractInvitation implements Invitation {
    protected final String token;
    protected final boolean multi;
    private final InvitationType type;

    protected AbstractInvitation(@NotNull String token, boolean multi, InvitationType type) {
        this.token = token;
        this.multi = multi;
        this.type = type;
    }

    protected AbstractInvitation(Element element, InvitationType type) {
        this.token = element.getAttributeValue("token");
        this.multi = Boolean.valueOf(element.getAttributeValue("multi"));
        this.type = type;
    }

    @NotNull
    @Override
    public String getToken() {
        return token;
    }

    @NotNull
    @Override
    public ModelAndView processInvitationRequest(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        return new ModelAndView(new RedirectView("/registerUser.html", true));
    }

    @NotNull
    @Override
    public InvitationType getType() {
        return type;
    }

    public void writeTo(@NotNull Element element) {
        element.setAttribute("token", token);
        element.setAttribute("multi", multi + "");
    }

    @Override
    public boolean isReusable() {
        return !multi;
    }
}
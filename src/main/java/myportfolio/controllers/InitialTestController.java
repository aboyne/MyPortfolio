package myportfolio.controllers;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Named;
import java.io.Serializable;

@Log
@ConversationScoped
@Named(value = "initialTestController")
public class InitialTestController implements Serializable
{
    @Getter
    @Setter
    private String valueToStore;

    public void saveValue()
    {
        log.warning("Saving value: " + valueToStore);
    }
}



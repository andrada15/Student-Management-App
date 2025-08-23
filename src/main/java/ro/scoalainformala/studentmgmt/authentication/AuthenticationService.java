package ro.scoalainformala.studentmgmt.authentication;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest authenticationDO);
    AuthenticationResponse register(AuthenticationRequest authenticationDO);

}

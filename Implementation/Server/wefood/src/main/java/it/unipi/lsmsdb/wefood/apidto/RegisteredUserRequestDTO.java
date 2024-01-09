package it.unipi.lsmsdb.wefood.apidto;

import it.unipi.lsmsdb.wefood.dto.RegisteredUserDTO;

public class RegisteredUserRequestDTO {

    private RegisteredUserDTO registeredUserDTO;
    private String usernameToFollow;

    public RegisteredUserRequestDTO(RegisteredUserDTO registeredUserDTO, String usernameToFollow) {
        this.registeredUserDTO = registeredUserDTO;
        this.usernameToFollow = usernameToFollow;
    }

    public RegisteredUserDTO getRegisteredUserDTO() {
        return registeredUserDTO;
    }

    public void setRegisteredUserDTO(RegisteredUserDTO registeredUserDTO) {
        this.registeredUserDTO = registeredUserDTO;
    }

    public String getUsernameToFollow() {
        return usernameToFollow;
    }

    public void setUsernameToFollow(String usernameToFollow) {
        this.usernameToFollow = usernameToFollow;
    }
}

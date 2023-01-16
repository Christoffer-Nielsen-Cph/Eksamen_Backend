package dtos;

import entities.User;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserDTO {

    private String userName;
    private String userEmail;
    private String userPass;

    private int userPhone;

    private int userBillingPrHour;
    private List<String> roleList;

    public UserDTO(User user){
        this.userName = user.getUserName();
        this.userEmail = user.getUserEmail();
        this.userPass = user.getUserPass();
        this.userPhone = user.getUserPhone();
        this.userBillingPrHour = user.getUserBillingPrHour();
        this.roleList = user.getRolesAsStrings();
    }
    public User getEntity(){
        User user = new User();
        if(this.userName != null){
            user.setUserName(this.userName);
        }
        user.setUserEmail(this.userEmail);
        user.setUserPass(this.userPass);
        user.setUserPhone(this.userPhone);
        user.setUserBillingPrHour(this.userBillingPrHour);
        user.getRolesAsStrings();
        return user;
    }

    public static List<UserDTO> getUserDTOs(List<User> users){
        List<UserDTO> userDTOs = new ArrayList();
        users.forEach(user->userDTOs.add(new UserDTO(user)));
        return userDTOs;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public int getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(int userPhone) {
        this.userPhone = userPhone;
    }

    public int getUserBillingPrHour() {
        return userBillingPrHour;
    }

    public void setUserBillingPrHour(int userBillingPrHour) {
        this.userBillingPrHour = userBillingPrHour;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return getUserName().equals(userDTO.getUserName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName());
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "userName='" + userName + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPass='" + userPass + '\'' +
                ", userPhone=" + userPhone +
                ", userBillingPrHour=" + userBillingPrHour +
                ", roleList=" + roleList +
                '}';
    }
}

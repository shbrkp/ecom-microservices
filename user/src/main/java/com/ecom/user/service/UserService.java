package com.ecom.user.service;


import com.ecom.user.dto.AddressDTO;
import com.ecom.user.dto.UserRequest;
import com.ecom.user.dto.UserResponse;
import com.ecom.user.model.Address;
import com.ecom.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final com.ecom.user.repository.UserRepository userRepository;
    private List<com.ecom.user.model.User> userList = new ArrayList();

    public List<com.ecom.user.dto.UserResponse> getAllusers()
    {
       // return userRepository.findAll();

        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }
    public void adduser(UserRequest userRequest){
        User user = new User();
        updateUserFromRequest(user,userRequest);
        System.out.println("user---->"+user.getId());
        user = userRepository.save(user);
        System.out.println("user---->"+user.getId());
    }

    private void updateUserFromRequest(User user,UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhone(userRequest.getPhone());
        user.setEmail(userRequest.getEmail());
        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setCity(userRequest.getAddress().getCity());
            address.setCountry(userRequest.getAddress().getCountry());
            user.setAddress(address);
        }
    }

    public boolean updateUser(UserRequest updatedUserrequest, String id){

        // code for getting list and seraching and then update
        /*userList = userRepository.findAll();
        return userList.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    existingUser.setId(id);
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);*/
        // code for getting object and then update

        return userRepository.findById(String.valueOf(id))
                .map(existingUser -> {
                    updateUserFromRequest(existingUser,updatedUserrequest);
                    existingUser.setId(String.valueOf(id));
                    userRepository.save(existingUser);
                    return true;
                })
                .orElse(false);



        /*Optional<User> userfromDB = Optional.of(new User());
        userfromDB = userRepository.findById(id);
        user.setAddress();
        userRepository.save(user);*/
    }

    public Optional<UserResponse> fetchUser(String id)
    {

        /*return userList.stream()
                .filter(user -> user.getId().eq*/

      // return userRepository.findById(id);

        return userRepository.findById(String.valueOf(id))
                .map(this::mapToUserResponse);
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse =  new UserResponse();
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        //userResponse.setId(String.valueOf(user.getId()));
        userResponse.setEmail(user.getEmail());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());
        if(user.getAddress() != null){
            AddressDTO addressDTO = new AddressDTO();
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setStreet(user.getAddress().getStreet());
            userResponse.setAddressDTO(addressDTO);
        }
        return userResponse;
    }
}

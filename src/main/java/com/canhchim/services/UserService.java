package com.canhchim.services;

import com.canhchim.models.ShpRole;
import com.canhchim.models.ShpShopEmployee;
import com.canhchim.models.ShpUser;
import com.canhchim.payload.request.UserRegisterRequest;
import com.canhchim.repositories.ShpRoleRepository;
import com.canhchim.repositories.ShpShopEmployeeRepository;
import com.canhchim.repositories.ShpShopRepository;
import com.canhchim.repositories.ShpUserRepository;
import com.canhchim.securityconfig.customuserdetail.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService
{
    ShpUserRepository userRepository;
    ShpShopRepository shopRepository;
    ShpRoleRepository roleRepository;
    ShpShopEmployeeRepository shopEmployeeRepository;
    private final Long[] defaultRole = {1L, 2L, 3L};

    /**
     * @param username
     * @return the userdetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        ShpUser user = userRepository.findByUserName(username)
                                     .orElseThrow(() -> new UsernameNotFoundException("Username does not exist"));
        Set<GrantedAuthority> authorities = new HashSet<>();
        user.getRoles()
            .stream()
            .map(ShpRole::getRoleName)
            .forEach(roleName -> authorities.add(new SimpleGrantedAuthority(roleName)));
        return new CustomUserDetails(
                user.getUserName(),
                user.getPassword(),
                user.getShop().stream()
                    .map(sh -> sh.getShop().getId())
                    .collect(Collectors.toSet()),
                authorities
        );
    }

    public ShpUser save(ShpUser newUser)
    {
        return userRepository.save(newUser);
    }

    public boolean register(UserRegisterRequest userRegister) throws Exception
    {
        Set<ShpRole> roles = new HashSet<>();
        var newUser = new ShpUser();
        newUser.setUserName(userRegister.getUsername());
        newUser.setPassword(userRegister.getPassword());
        newUser.setPhone(userRegister.getPhoneNumber());
        newUser.setIsOwner(userRegister.getIsOwner());
        newUser.setCitizenIdentity(userRegister.getCitizenIdentity());
        newUser.setFullName(userRegister.getFullName());

        if ( userRepository.existsByUserName(newUser.getUserName()) ) {
            throw new Exception("Username already existed.");
        }
        if ( userRepository.existsByCitizenIdentity(newUser.getCitizenIdentity()) ) {
            throw new Exception("Citizen ID already existed.");
        }
        if ( userRepository.existsByPhone(newUser.getPhone()) ) {
            throw new Exception("Phone number already existed");
        }
        if ( userRegister.getRoleId() == null || Arrays.stream(userRegister.getRoleId()).findAny().isEmpty() ) {
            userRegister.setRoleId(this.defaultRole);
        }
        Arrays.asList(userRegister.getRoleId())
              .forEach(role -> {
                  roles.add(roleRepository.findById(role).orElse(null));
              });
        newUser.setRoles(roles);
        try {
            var savedUser = userRepository.save(newUser);

            var shop = shopRepository.findById(userRegister.getShopId())
                                     .orElseThrow(() -> new RuntimeException("Could not find shop Id."));
            var shopEmp = new ShpShopEmployee();
            shopEmp.setShop(shop);

            shopEmp.setUser(savedUser);
            shopEmployeeRepository.save(shopEmp);
            return true;
        }
        catch ( Exception e ) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Map<Long, String> displayAllRole()
    {
        var roles = roleRepository.findAll();
        Map<Long, String> roleMap = new HashMap<>();
        roles.forEach(r -> {
            roleMap.put(r.getId(), r.getDescription());
        });
        return roleMap;
    }

    public Map<Long, String> displayRolesOfUser(Long userId) throws Exception
    {
        var foundUser = userRepository.findById(userId);
        var result = new HashMap<Long, String>();
        if ( foundUser.isEmpty() ) {throw new Exception("User's Id is not presented");} ;
        foundUser.get().getRoles().forEach(r -> result.put(r.getId(), r.getDescription()));
        return result;
    }

    public void setRoles(@NotNull ShpUser user, Long[] roleId)
    {
        var roleList = Arrays.stream(roleId)
                             .filter(id -> roleRepository.existsById(id))
                             .map(id -> roleRepository.findById(id).orElse(null))
                             .collect(Collectors.toSet());
        user.setRoles(roleList);
        save(user);
    }

    public ShpUser findByName(String name)
    {
        return userRepository.findByUserName(name).orElse(null);
    }

    public ShpUser findById(long userId)
    {
        return userRepository.findById(userId).orElse(null);
    }
}

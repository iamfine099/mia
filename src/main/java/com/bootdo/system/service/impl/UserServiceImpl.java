package com.bootdo.system.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.config.BootdoConfig;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.utils.BuildTree;
import com.bootdo.common.utils.ContextHolderUtils;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.system.dao.DeptDao;
import com.bootdo.system.dao.RoleDao;
import com.bootdo.system.dao.UserDao;
import com.bootdo.system.dao.UserRoleDao;
import com.bootdo.system.domain.DeptDO;
import com.bootdo.system.domain.RoleDO;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.domain.UserRoleDO;
import com.bootdo.system.service.UserService;
import com.bootdo.system.vo.UserVO;

//@CacheConfig(cacheNames = "user")
@Transactional
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userMapper;
    @Autowired
    UserRoleDao userRoleMapper;
    @Autowired
    DeptDao deptMapper;
    @Autowired
    private BootdoConfig bootdoConfig;
    @Autowired
    RoleDao roleDao;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
//    @Cacheable(key = "#id")
    public UserDO get(Long id) {
        if (id == null || id == 0) return new UserDO();
        List<Long> roleIds = userRoleMapper.listRoleId(id);
        UserDO user = userMapper.get(id);
        if (user.getDeptId() != null) {
            user.setDeptName(deptMapper.get(user.getDeptId()).getName());
        }
        user.setRoleIds(roleIds);
        return user;
    }

    @Override
    public List<UserDO> list(Map<String, Object> map) {
        return userMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return userMapper.count(map);
    }

    @Transactional
    @Override
    public int save(UserDO user) {
        List<Long> roles = user.getRoleIds();
        if (roles != null && roles.size() > 0) {
            RoleDO role = roleDao.get(roles.get(0));
            if (role != null) {
                user.setfType(role.getfType());
                if ("P".equals(role.getfType())) {
                    user.setBusId("0");
                }
            }
        }
        int count = userMapper.save(user);
        Long userId = user.getUserId();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Long roleId : roles) {
                UserRoleDO ur = new UserRoleDO();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return count;
    }

    @Override
    public int update(UserDO user) {

        List<Long> roles = user.getRoleIds();
        if (roles != null && roles.size() > 0) {
            RoleDO role = roleDao.get(roles.get(0));
            if (role != null) {
                user.setfType(role.getfType());
                if ("P".equals(role.getfType())) {
                    user.setBusId("0");
                }
            }
        }
        int r = userMapper.update(user);
        Long userId = user.getUserId();
        userRoleMapper.removeByUserId(userId);
        List<UserRoleDO> list = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Long roleId : roles) {
                UserRoleDO ur = new UserRoleDO();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                list.add(ur);
            }
        }
        if (list.size() > 0) {
            userRoleMapper.batchSave(list);
        }
        return r;
    }

    @Override
    public int remove(Long userId) {
        userRoleMapper.removeByUserId(userId);
        return userMapper.remove(userId);
    }

    @Override
    public boolean exit(Map<String, Object> params) {
        boolean exit;
        exit = userMapper.list(params).size() > 0;
        return exit;
    }

    @Override
    public Set<String> listRoles(Long userId) {
        return null;
    }

    @Override
    public int resetPwd(UserVO userVO, UserDO userDO) throws Exception {
        if (Objects.equals(userVO.getUserDO().getUserId(), userDO.getUserId())) {
            if (Objects.equals(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdOld()), userDO.getPassword())) {
                userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
                return userMapper.update(userDO);
            } else {
                throw new Exception("输入的旧密码有误！");
            }
        } else {
            throw new Exception("你修改的不是你登录的账号！");
        }
    }

    @Override
    public int adminResetPwd(UserVO userVO) throws Exception {
        UserDO userDO = get(userVO.getUserDO().getUserId());
        if ("admin".equals(userDO.getUsername())) {
            throw new Exception("超级管理员的账号不允许直接重置！");
        }
        userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), userVO.getPwdNew()));
        return userMapper.update(userDO);


    }

    @Transactional
    @Override
    public int batchremove(Long[] userIds) {
        int count = userMapper.batchRemove(userIds);
        userRoleMapper.batchRemoveByUserId(userIds);
        return count;
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        List<DeptDO> depts = deptMapper.list(new HashMap<String, Object>(16));
        Long[] pDepts = deptMapper.listParentDept();
        Long[] uDepts = userMapper.listAllDept();
        Long[] allDepts = (Long[]) ArrayUtils.addAll(pDepts, uDepts);
        for (DeptDO dept : depts) {
            if (!ArrayUtils.contains(allDepts, dept.getDeptId())) {
                continue;
            }
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(dept.getDeptId().toString());
            tree.setParentId(dept.getParentId().toString());
            tree.setText(dept.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "dept");
            tree.setState(state);
            trees.add(tree);
        }
        List<UserDO> users = userMapper.list(new HashMap<String, Object>(16));
        for (UserDO user : users) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(user.getUserId().toString());
            tree.setParentId(user.getDeptId().toString());
            tree.setText(user.getName());
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            state.put("mType", "user");
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees);
        return t;
    }

    @Override
    public int updatePersonal(UserDO userDO) {
        return userMapper.update(userDO);
    }

//    @Override
//    public Map<String, Object> updatePersonalImg(MultipartFile file, String avatar_data, Long userId) throws Exception {
//        String fileOldName = file.getOriginalFilename();
//        String fileName = FileUtil.renameToUUID(fileOldName);
//        FileDO sysFile = new FileDO(FileType.fileType(fileName), "/files/" + fileName, new Date(),fileOldName);
//        //获取图片后缀
//        String prefix = fileName.substring((fileName.lastIndexOf(".") + 1));
//        String[] str = avatar_data.split(",");
//        //获取截取的x坐标
//        int x = (int) Math.floor(Double.parseDouble(str[0].split(":")[1]));
//        //获取截取的y坐标
//        int y = (int) Math.floor(Double.parseDouble(str[1].split(":")[1]));
//        //获取截取的高度
//        int h = (int) Math.floor(Double.parseDouble(str[2].split(":")[1]));
//        //获取截取的宽度
//        int w = (int) Math.floor(Double.parseDouble(str[3].split(":")[1]));
//        //获取旋转的角度
//        int r = Integer.parseInt(str[4].split(":")[1].replaceAll("}", ""));
//        try {
//            BufferedImage cutImage = ImageUtils.cutImage(file, x, y, w, h, prefix);
//            BufferedImage rotateImage = ImageUtils.rotateImage(cutImage, r);
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            boolean flag = ImageIO.write(rotateImage, prefix, out);
//            //转换后存入数据库
//            byte[] b = out.toByteArray();
//            FileUtil.uploadFile(b, bootdoConfig.getUploadPath(), fileName);
//        } catch (Exception e) {
//            throw new Exception("图片裁剪错误！！");
//        }
//        Map<String, Object> result = new HashMap<>();
//        if (sysFileService.save(sysFile) > 0) {
//            UserDO userDO = new UserDO();
//            userDO.setUserId(userId);
//            if (userMapper.update(userDO) > 0) {
//                result.put("url", sysFile.getUrl());
//            }
//        }
//        return result;
//    }

    /**
     * 绑定openid
     */
    public void bandOpenid() {

        HttpSession session = ContextHolderUtils.getSession();
        String openid = (String) session.getAttribute("openid");
        //解决重新绑定微信问题，清理原有关联的微信账号
        userMapper.updateOpenid(openid);
//    	UserDO userDo= ShiroUtils.getUser().getUser();
//    	userDo.setOpenid(openid);
//    	userMapper.update(userDo);
    }

    @Override
    public int only(Map<String, Object> map) {
        return userMapper.only(map);
    }

    @Override
    public int resetpwd(String busId, String fType, String password) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("busId", busId);
        map.put("fType", fType);
        UserDO user = userMapper.getIdType(map);
        user.setPassword(MD5Utils.encrypt(user.getUsername(), password));
        return userMapper.update(user);
    }

    @Override
    public UserDO getIdType(Map<String, Object> map) {
        return userMapper.getIdType(map);
    }

    @Override
    public Map<String, Object> updatePersonalImg(MultipartFile file,
                                                 String avatar_data, Long userId) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int removeByParam(Map<String, Object> map) {
        // TODO Auto-generated method stub
        return userMapper.removeByParam(map);
    }

}

package babinski.sebastian.SFTPcon.controller;

import babinski.sebastian.SFTPcon.service.SFTPService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("file/download")
public class FileDownloadController {

    private SFTPService sftpService;

    @Autowired
    public FileDownloadController(SFTPService sftpService) {
        this.sftpService = sftpService;
    }

    @PostMapping
    public void downloadFile(
            @RequestParam("remoteFile") String remoteFile,
            @RequestParam("address") String sshServerAddress,
            @RequestParam("port") Integer sshServerPort,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("localPath") String localPath) throws JSchException, SftpException, IOException {

        sftpService.downloadFile(
                sshServerAddress,
                sshServerPort,
                username,
                password,
                remoteFile,
                localPath
        );
    }
}

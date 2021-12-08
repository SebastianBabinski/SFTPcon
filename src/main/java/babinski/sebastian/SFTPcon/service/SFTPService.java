package babinski.sebastian.SFTPcon.service;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class SFTPService {

    public void downloadFile(String serverAddress,
                             Integer serverPort,
                             String username,
                             String password,
                             String remoteFile,
                             String localPath) throws JSchException, SftpException, IOException {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // setting up JSch, enabling password authentication
            JSch jSch = new JSch();
            session = jSch.getSession(username, serverAddress, serverPort);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            // downloading
            channelSftp.get(remoteFile, localPath);
            channelSftp.disconnect();
            session.disconnect();
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public void uploadFile(String serverAddress,
                           Integer serverPort,
                           String username,
                           String password,
                           MultipartFile file,
                           String destinationPath) throws JSchException, SftpException, IOException {
        Session session = null;
        ChannelSftp channelSftp = null;

        try {
            // setting up JSch, enabling password authentication
            JSch jSch = new JSch();
            session = jSch.getSession(username, serverAddress, serverPort);
            session.setPassword(password);

            java.util.Properties config = new java.util.Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            channelSftp.cd(destinationPath);
            // uploading
            channelSftp.put(file.getInputStream(), file.getOriginalFilename(), ChannelSftp.OVERWRITE);

            channelSftp.disconnect();
            session.disconnect();
        } finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }

            if (session != null && session.isConnected()) {
                session.disconnect();
            }
        }
    }
}

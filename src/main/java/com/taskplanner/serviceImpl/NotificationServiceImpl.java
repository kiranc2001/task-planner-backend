package com.taskplanner.serviceImpl;

import com.taskplanner.helper.EmailHelper;
import com.taskplanner.model.Notification;
import com.taskplanner.model.Task;
import com.taskplanner.model.Status;
import com.taskplanner.repository.NotificationRepository;
import com.taskplanner.repository.TaskRepository;
import com.taskplanner.repository.UserRepository;
import com.taskplanner.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private EmailHelper emailHelper;

    @Scheduled(fixedRate = 30000)
    //@Scheduled(cron = "0 0 9 * * ?")  // Daily at 9 AM
    @Transactional
    @Override
    public void sendReminders() {
        // For demo/multi-user: Loop over users or use findAll; here, query all due-soon across users
        List<Task> dueSoonTasks = taskRepository.findAll();  // Or implement per-user if needed
        for (Task task : dueSoonTasks) {
            if (task.getDueDate() != null &&
                    task.getDueDate().isBefore(LocalDateTime.now().plusHours(24)) &&
                    task.getStatus() != Status.COMPLETED) {
                String message = "Reminder: Task '" + task.getTitle() + "' is due soon on " + task.getDueDate() + "!";
                emailHelper.sendEmail(task.getUser().getEmail(), "Task Due Reminder", message);

                Notification notif = new Notification();
                notif.setUser(task.getUser());
                notif.setMessage(message);
                notificationRepository.save(notif);
            }
        }
    }
}
package com.lbms.controller.librarian;

import com.lbms.dao.UserDAO;
import com.lbms.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LibrarianHomeController {

    private static final Logger logger = Logger.getLogger(LibrarianHomeController.class.getName());

    @FXML private Button addLibrarianBtn;
    @FXML private TableView<User> librarianTable;
    @FXML private TableColumn<User, String> colName;
    @FXML private TableColumn<User, String> colRole;
    @FXML private TableColumn<User, String> colPhone;
    @FXML private TableColumn<User, Void> colActions;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    private void initialize() {
        librarianTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        addLibrarianBtn.setOnAction(e -> openAddLibrarianForm());

        colName.setCellValueFactory(new PropertyValueFactory<>("username"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        addActionButtons();
        loadLibrariansAsync();
    }

    private void loadLibrariansAsync() {
        Task<ObservableList<User>> task = new Task<>() {
            @Override
            protected ObservableList<User> call() {
                try {
                    return FXCollections.observableArrayList(userDAO.getAllLibrarians());
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Failed to fetch librarians from database", ex);
                    return FXCollections.observableArrayList();
                }
            }
        };

        task.setOnSucceeded(e -> librarianTable.setItems(task.getValue()));
        task.setOnFailed(e -> logger.log(Level.SEVERE, "Failed to load librarians asynchronously", task.getException()));

        new Thread(task).start();
    }

    private void addActionButtons() {
        colActions.setCellFactory(col -> new TableCell<>() {

            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Delete");
            private final HBox pane = new HBox(10, editBtn, deleteBtn);

            {
                pane.setAlignment(Pos.CENTER);

                editBtn.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    try {
                        handleEdit(user);
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, "Error while handling edit", ex);
                    }
                });

                deleteBtn.setOnAction(e -> {
                    User user = getTableView().getItems().get(getIndex());
                    handleDeleteAsync(user);
                });
            }

            private void handleEdit(User user) {
                System.out.println("Edit clicked: " + user.getUsername());
            }

            private void handleDeleteAsync(User user) {
                Task<Boolean> task = new Task<>() {
                    @Override
                    protected Boolean call() {
                        try {
                            return userDAO.deleteUser(user.getId());
                        } catch (Exception ex) {
                            logger.log(Level.SEVERE, "Failed to delete user: " + user.getUsername(), ex);
                            return false;
                        }
                    }
                };

                task.setOnSucceeded(e -> {
                    if (task.getValue()) {
                        loadLibrariansAsync();
                    }
                });

                task.setOnFailed(e -> logger.log(Level.SEVERE, "Delete task failed for user: " + user.getUsername(), task.getException()));

                new Thread(task).start();
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : pane);
            }
        });
    }

    private void openAddLibrarianForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/librarian/AddLibrarianForm.fxml"));
            AnchorPane pane = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add Librarian");
            stage.setScene(new Scene(pane));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);

            stage.showAndWait();
            loadLibrariansAsync();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load Add Librarian form", e);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error while opening Add Librarian form", e);
        }
    }
}

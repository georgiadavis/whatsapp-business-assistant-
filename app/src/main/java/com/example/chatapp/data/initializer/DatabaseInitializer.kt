package com.example.chatapp.data.initializer

import com.example.chatapp.data.generator.ChatDataGenerator
import com.example.chatapp.data.repository.ChatRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import android.util.Log

@Singleton
class DatabaseInitializer @Inject constructor(
    private val chatRepository: ChatRepository
) {
    
    suspend fun initializeDatabase() = withContext(Dispatchers.IO) {
        try {
            Log.d("DatabaseInitializer", "Starting database initialization...")
            
            // For now, always populate the database on fresh installs
            // This ensures the database tables are created and populated
            Log.d("DatabaseInitializer", "Populating database...")
            populateDatabase()
            Log.d("DatabaseInitializer", "Database initialization completed successfully")
            
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Error during initialization: ${e.message}", e)
            throw e
        }
    }
    
    private suspend fun populateDatabase() {
        try {
            Log.d("DatabaseInitializer", "Starting database population...")
            
            // Database will be created automatically by Room when we first access it
            Log.d("DatabaseInitializer", "Database will be created automatically by Room")
            
            // Generate users
            Log.d("DatabaseInitializer", "Generating users...")
            val users = ChatDataGenerator.generateUsers(100)
            Log.d("DatabaseInitializer", "Generated ${users.size} users")
            chatRepository.insertUsers(users)
            Log.d("DatabaseInitializer", "Users inserted successfully")
            
            // Use the first user as the current user
            val currentUserId = "user_1"
            
            // Generate conversations
            Log.d("DatabaseInitializer", "Generating conversations...")
            val conversations = ChatDataGenerator.generateConversations(users, currentUserId)
            Log.d("DatabaseInitializer", "Generated ${conversations.size} conversations")
            
            // Insert conversations FIRST (before participants)
            Log.d("DatabaseInitializer", "Inserting conversations...")
            chatRepository.insertConversations(conversations)
            Log.d("DatabaseInitializer", "Conversations inserted successfully")
            
            // Generate participants
            Log.d("DatabaseInitializer", "Generating participants...")
            val participants = ChatDataGenerator.generateConversationParticipants(
                conversations, users, currentUserId
            )
            Log.d("DatabaseInitializer", "Generated ${participants.size} participants")
            chatRepository.insertParticipants(participants)
            Log.d("DatabaseInitializer", "Participants inserted successfully")
            
            // Generate messages
            Log.d("DatabaseInitializer", "Generating messages...")
            val messages = ChatDataGenerator.generateMessages(
                conversations, participants, messagesPerConversation = 50
            )
            Log.d("DatabaseInitializer", "Generated ${messages.size} messages")
            chatRepository.insertMessages(messages)
            Log.d("DatabaseInitializer", "Messages inserted successfully")
            
            // Update conversations with last message info and correct unread counts
            Log.d("DatabaseInitializer", "Updating conversations with last message info...")
            val updatedConversations = ChatDataGenerator.updateConversationsWithLastMessage(
                conversations, messages, currentUserId
            )
            Log.d("DatabaseInitializer", "Updated ${updatedConversations.size} conversations")
            
            // Update the existing conversations with last message info
            Log.d("DatabaseInitializer", "Updating conversations in database...")
            chatRepository.updateConversations(updatedConversations)
            Log.d("DatabaseInitializer", "Conversations updated successfully")

            Log.d("DatabaseInitializer", "Database initialized with ${users.size} users, ${updatedConversations.size} conversations, and ${messages.size} messages")
        } catch (e: Exception) {
            Log.e("DatabaseInitializer", "Error during population: ${e.message}", e)
            throw e
        }
    }
    
    suspend fun resetAndRepopulateDatabase() = withContext(Dispatchers.IO) {
        try {
            println("DatabaseInitializer: Starting database reset...")
            chatRepository.clearAllData()
            println("DatabaseInitializer: All data cleared successfully")
            populateDatabase()
            println("DatabaseInitializer: Database reset and repopulation completed")
        } catch (e: Exception) {
            println("DatabaseInitializer: Error during reset: ${e.message}")
            e.printStackTrace()
            throw e
        }
    }
}

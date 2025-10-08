package com.example.chatapp.data.generator

import com.example.chatapp.data.local.entity.*
import kotlinx.coroutines.delay
import java.util.UUID
import kotlin.random.Random
import kotlin.math.absoluteValue

object ChatDataGenerator {
    
    private val firstNames = listOf(
        "Emma", "Liam", "Olivia", "Noah", "Ava", "Elijah", "Sophia", "Lucas",
        "Isabella", "Mason", "Mia", "James", "Charlotte", "Benjamin", "Amelia",
        "Jacob", "Harper", "Michael", "Evelyn", "Ethan", "Abigail", "Alexander",
        "Emily", "William", "Elizabeth", "Daniel", "Sofia", "Matthew", "Avery",
        "Aiden", "Ella", "Joseph", "Scarlett", "Jackson", "Grace", "David",
        "Chloe", "Carter", "Victoria", "Wyatt", "Riley", "John", "Aria",
        "Owen", "Lily", "Dylan", "Aubrey", "Luke", "Zoey", "Gabriel", "Penelope"
    )
    
    private val lastNames = listOf(
        "Smith", "Johnson", "Williams", "Brown", "Jones", "Garcia", "Miller",
        "Davis", "Rodriguez", "Martinez", "Hernandez", "Lopez", "Gonzalez",
        "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "Martin",
        "Lee", "Perez", "Thompson", "White", "Harris", "Sanchez", "Clark",
        "Ramirez", "Lewis", "Robinson", "Walker", "Young", "Allen", "King",
        "Wright", "Scott", "Torres", "Nguyen", "Hill", "Flores", "Green",
        "Adams", "Nelson", "Baker", "Hall", "Rivera", "Campbell", "Mitchell",
        "Carter", "Roberts"
    )
    
    private val statusMessages = listOf(
        "Available", "Busy", "In a meeting", "Working from home", "On vacation",
        "Out of office", "Do not disturb", "Away", "Be right back", "In a call",
        "Coding...", "Coffee break ☕", "Living the dream", "Stay positive!",
        "Working hard", "TGIF!", "Monday blues", "Deadline approaching",
        "Learning something new", "Building awesome stuff", null, null, null
    )
    
    private val shortMessages = listOf(
        // Natural short responses and reactions
        "Hey!", "Hi there!", "Yes!", "Nope", "OK", "Sure thing", "Thanks!", "np", "Maybe", "omw",
        "Sounds good", "Perfect!", "Got it", "On it", "Will do", "Can't today", "I'm here", "Almost there",
        "haha", "lol", "😂", "For real", "No way!", "Seriously?", "That's crazy", "Wow",
        "Miss you", "Love this", "So true", "Exactly!", "Same here", "Agreed", "Let's go!", "I'm in",
        "5 mins", "Running late", "Be there soon", "Just left", "Stuck in traffic", "On my way",
        "Coffee?", "Lunch?", "Free?", "Busy rn", "Call me", "Text me", "Check this out", "Look at this"
    )
    
    private val mediumMessages = listOf(
        // Medium length messages that fill more of the preview line
        "Hey! How are you doing today? Hope everything is going well on your end",
        "Just wanted to check in and see how your week has been going so far",
        "Good morning! Did you manage to get that thing sorted out yesterday?",
        "The presentation went really well, everyone was impressed with your ideas",
        "I'm thinking about grabbing lunch at that new place downtown, want to join?",
        "Did you see what happened at the meeting? That was quite unexpected",
        "Thanks for helping me out yesterday, really appreciate your support",
        "Can we reschedule our call to tomorrow afternoon? Something came up",
        "Just finished reading that book you recommended, it was amazing!",
        "The weather is perfect today, thinking about going for a long walk",
        "Have you had a chance to review the document I sent over yesterday?",
        "I'm running a bit late but should be there in about 15 minutes",
        "That restaurant you suggested was fantastic, we should go again soon",
        "Working on the new project proposal, will send it your way shortly",
        "Can't believe it's already Thursday, this week has flown by so quickly",
        "The client loved our presentation! They want to move forward immediately",
        "Just got back from the gym, feeling great and ready for the day",
        "Remember to bring those files to the meeting tomorrow morning",
        "I've been thinking about what you said yesterday, you make a good point",
        "The kids are doing great, they miss you and can't wait to see you",
        "Traffic is terrible this morning, might be a few minutes late to the office",
        "Did you catch the game last night? That final quarter was incredible!",
        "Just booked our tickets for the trip, so excited about this vacation!",
        "The new update looks fantastic, great work on implementing those changes",
        "Can you believe what happened in the news today? Absolutely shocking",
        "I'll be working from home tomorrow, but available for calls all day",
        "Thanks for sending over those photos, they brought back great memories",
        "The team meeting is at 3 PM in the conference room, don't forget",
        "Just saw your post on social media, that's such wonderful news!",
        "Let me know when you're free to discuss the budget for next quarter",
        "The dinner party was lovely, thanks so much for inviting us over",
        "I've been meaning to ask you about that project you mentioned last week",
        "Everything is set for tomorrow's event, just need to confirm the catering",
        "Have you tried that new coffee shop on Main Street? Their pastries are amazing",
        "The doctor said everything looks good, nothing to worry about at all",
        "Can you send me the updated spreadsheet when you get a chance?",
        "Just finished the marathon! Can't believe I actually did it, feeling accomplished",
        "The kids' school called, apparently there's an early dismissal today",
        "Looking forward to the weekend, planning to finally relax and unwind",
        "That movie you recommended was incredible, thanks for the suggestion!",
        "The contractor said they can start the renovation next Monday morning",
        "I'm at the store now, do you need me to pick up anything while I'm here?",
        "Great catching up with you yesterday, we should do that more often",
        "The flight has been delayed by two hours, will keep you posted",
        "Just submitted the final report, feels good to have that completed",
        "The interview went really well, fingers crossed for good news soon",
        "Can you believe it's been five years since we graduated? Time flies!",
        "The package arrived this morning, everything looks perfect, thank you",
        "Working late tonight to finish this project, but almost done now",
        "Your presentation skills have really improved, keep up the great work!"
    )
    
    private val longMessages = listOf(
        // Long, detailed messages
        "Hey there! I hope you're having a fantastic day. I just wanted to check in and see how everything is going with your project. I've been working on some really interesting stuff lately and would love to get your thoughts on it when you have a moment.",
        
        "Good morning! I wanted to let you know that the meeting yesterday went really well. Everyone was impressed with your presentation and the ideas you brought to the table. I think we're going to move forward with the new approach you suggested.",
        
        "Hi! I'm so excited to tell you about this amazing restaurant I discovered last night. The food was absolutely incredible - they have this chef who specializes in fusion cuisine, and the atmosphere is just perfect for both casual dinners and special occasions.",
        
        "Hey! I've been thinking about our conversation from the other day, and I wanted to share some additional thoughts I had. The more I reflect on it, the more I realize how important it is to consider the long-term implications of our decisions.",
        
        "Good evening! I hope your day has been productive. I wanted to follow up on the email I sent earlier about the upcoming project deadline. I know we're all busy, but I think it's crucial that we stay on track to meet our goals.",
        
        "Hi there! I just finished reading this incredible book that I think you would absolutely love. It's about this fascinating journey of self-discovery and personal growth, and the author has such a unique perspective on life that really resonated with me.",
        
        "Hey! I wanted to share some exciting news with you. I've been working on a new project for the past few months, and it's finally starting to come together. It's been quite a journey with lots of challenges, but I'm really proud of how it's turning out.",
        
        "Good morning! I hope you're well. I wanted to discuss something important that came up during our team meeting yesterday. There are some new opportunities on the horizon that I think could be really beneficial for all of us, and I'd love to get your input.",
        
        "Hi! I'm so grateful for our friendship and wanted to take a moment to tell you how much I appreciate having you in my life. You've been such a supportive and understanding person, and I feel really lucky to know you.",
        
        "Hey there! I've been reflecting on our recent conversations and wanted to share some thoughts that have been on my mind. I think we're at a really important point in our journey, and I believe we have the opportunity to make some meaningful changes.",
        
        "Good evening! I hope your day has been wonderful. I wanted to let you know that I've been thinking about our plans for the weekend, and I have some exciting ideas that I think could make it really special. I can't wait to share them with you!",
        
        "Hi! I just wanted to check in and see how you're doing. I know things have been pretty hectic lately, and I wanted to make sure you're taking care of yourself. Remember that it's okay to take breaks and prioritize your well-being.",
        
        "Hey! I've been working on this really challenging project for the past few weeks, and I wanted to share some of the insights I've gained along the way. It's been quite a learning experience, and I think there are some valuable lessons here.",
        
        "Good morning! I hope you slept well. I wanted to discuss something that's been on my mind regarding our upcoming plans. I think we have a great opportunity to make this really special, and I'd love to brainstorm some ideas together.",
        
        "Hi there! I'm so excited to tell you about this amazing experience I had yesterday. It was one of those moments that really puts things into perspective and makes you appreciate the little things in life. I can't wait to share all the details with you!"
    )
    
    private val imageDescriptions = listOf(
        "Beautiful sunset", "My new pet", "Delicious meal", "Weekend vibes",
        "Office view", "Travel memories", "Family gathering", "Birthday celebration",
        "Nature photography", "City lights", "Beach day", "Mountain hiking",
        "Coffee art", "Home setup", "Workout progress", "Art project",
        "Garden flowers", "Cooking experiment", "Concert night", "Museum visit"
    )
    
    private val videoDescriptions = listOf(
        "Funny cat video", "Tutorial video", "Travel vlog", "Recipe video",
        "Workout routine", "Music video", "Movie trailer", "Product review",
        "Time-lapse", "Dance video", "Gaming highlights", "DIY project",
        "Comedy sketch", "News clip", "Sports highlights", "Behind the scenes"
    )
    
    private val linkTitles = listOf(
        "10 Tips for Productivity", "Breaking News Update", "New Technology Trends",
        "Health and Wellness Guide", "Recipe of the Day", "Stock Market Analysis",
        "Movie Review", "Travel Destinations", "Science Discovery", "Sports Update",
        "Fashion Trends", "Book Recommendation", "Music Album Release",
        "Restaurant Review", "Tech Product Launch", "Environmental News"
    )
    
    private val linkDescriptions = listOf(
        "An interesting article about recent developments",
        "Must-read insights on this topic",
        "Comprehensive guide with practical tips",
        "Latest updates and analysis",
        "Expert opinions and recommendations",
        "In-depth coverage of current events",
        "Useful resources and information",
        "Trending topics and discussions"
    )
    
    private val fileNames = listOf(
        "presentation.pdf", "report.docx", "spreadsheet.xlsx", "notes.txt",
        "proposal.pdf", "invoice.pdf", "contract.doc", "budget.xlsx",
        "meeting_notes.docx", "project_plan.pdf", "design.sketch", "mockup.fig",
        "code.zip", "dataset.csv", "analysis.ipynb", "slides.pptx"
    )
    
    // Avatar URLs using high-quality Unsplash portrait photos
    private val avatarUrls = listOf(
        // Professional portraits
        "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1494790108377-be9c29b29330?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1527980965255-d3b416303d12?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1438761681033-6461ffad8d80?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1488161628813-04466f872be2?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1502767089025-6572583495f9?w=200&h=200&fit=crop&crop=faces",
        
        // Diverse portraits
        "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1517365830460-955ce3ccd263?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1541823709867-1b206113eafd?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1542909168-82c3e7fdca5c?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1463453091185-61582044d556?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1514315384763-ba401779410f?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1541647376583-8934aaf3448a?w=200&h=200&fit=crop&crop=faces",
        
        // More professional headshots
        "https://images.unsplash.com/photo-1519345182560-3f2917c472ef?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1560250097-0b93528c311a?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1519085360753-af0119f7cbe7?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1582896911227-c966f6e7fb93?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1599566150163-29194dcaad36?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=200&h=200&fit=crop&crop=faces",
        
        // Casual portraits
        "https://images.unsplash.com/photo-1548142813-c348350df52b?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1512361436605-a484bdb34b5f?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1552374196-c4e7ffc6e126?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1528892952291-009c663ce843?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1520813792240-56fc4a3765a7?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1502823403499-6ccfcf4fb453?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1504703395950-b89145a5425b?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1499952127939-9bbf5af6c51c?w=200&h=200&fit=crop&crop=faces",
        
        // Additional diverse portraits
        "https://images.unsplash.com/photo-1551843073-4a9a5b6fcd5f?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1546961329-78bef0414d7c?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1583692331507-fc0bd348695d?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1598411072028-c4642d98352c?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1601412436009-d964bd02edbc?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1567532939604-b6b5b0db2604?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1597223557154-721c1cecc4b0?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1589571894960-20bbe2828d0a?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1558203728-00f45181dd84?w=200&h=200&fit=crop&crop=faces",
        "https://images.unsplash.com/photo-1565884280295-98eb83e41c65?w=200&h=200&fit=crop&crop=faces"
    )
    
    // Keep the old individual avatars list for backward compatibility
    private val individualAvatars = listOf(
        "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1517841905240-472988babdf9?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1527980965255-d3b416303d12?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1580489944761-15a19d654956?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1633332755192-727a05c4013d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1607746882042-944635dfe10e?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1619946794135-5bc917a27793?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1566492031773-4f4e44671d66?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1628157588553-5eeea00af15c?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1570295999919-56ceb5ecca61?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1582750433449-648ed127bb54?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1639149888905-fb39731f2e6c?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1612349317150-e413f6a5b16d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1601455763557-db1bea8a9a5a?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1554151228-14d9def656e4?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1547425260-76bcadfb4f2c?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1552058544-f2b08422138a?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1463453091185-61582044d556?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1508214751196-bcfd4ca60f91?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1521119989659-a83eee488004?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1531746020798-e6953c6e8e04?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1541101767792-f9b2b1c4f127?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1558618666-fcd25c85cd64?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1564564321837-a57b7070ac4f?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1595152772835-219674b2a8a6?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1568602471122-7832951cc4c5?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1590031905406-f18a426d772d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1598300042247-d088f8ab3a91?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1616766098956-c81f12114571?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1622253692010-333f2da6031d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1624298357597-fd92dfbec01d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1629425733761-caae3b5f2e50?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1634926878768-2a5b3c42f139?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1636041293178-808a6762ab39?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1637684666772-1f215bfd0f5d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1640951613773-54706e06851d?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1642790106117-e829e14a795f?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1645378999013-95abebf5f3c1?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1649972904349-6e44c42644a7?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1654110455429-cf322b40a906?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1661956602116-aa6865609028?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1669725687442-2c5c2c2b8bb0?w=150&h=150&fit=crop&crop=face",
        // Additional unique avatars to prevent duplicates
        "https://images.unsplash.com/photo-1487412720507-e7ab37603c6f?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1502685104226-ee32379fefbe?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1524504388940-b1c1722653e1?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1464863979621-258859e62245?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1459356979461-dae1b8dcb702?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1499996860823-5214fcc65f8f?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1495366691023-cc4eadcc2d7e?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1504703395950-b89145a5425b?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1503467913725-8484b65b0715?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1418065460487-3e41a6c84dc5?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1534528741775-53994a69daeb?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1517598024396-a772e91dd5b3?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1516726817505-f5ed825624d8?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1504439904031-93ded9f93e4e?w=150&h=150&fit=crop&crop=face",
        "https://images.unsplash.com/photo-1502767089025-6572583495f9?w=150&h=150&fit=crop&crop=face"
    )
    
    // Group avatar URLs using Unsplash group/team photos and emojis for special groups
    private val groupAvatars = mapOf(
        "Family" to "https://images.unsplash.com/photo-1511895426328-dc8714191300?w=200&h=200&fit=crop",
        "Work" to "https://images.unsplash.com/photo-1522071820081-009f0129c71c?w=200&h=200&fit=crop",
        "Friends" to "https://images.unsplash.com/photo-1529333166437-7750a6dd5a70?w=200&h=200&fit=crop",
        "School" to "https://images.unsplash.com/photo-1523050854058-8df90110c9f1?w=200&h=200&fit=crop",
        "College" to "https://images.unsplash.com/photo-1541339907198-e08756dedf3f?w=200&h=200&fit=crop",
        "University" to "https://images.unsplash.com/photo-1519452635265-7b1fbfd1e4e0?w=200&h=200&fit=crop",
        "Team" to "https://images.unsplash.com/photo-1557804506-669a67965ba0?w=200&h=200&fit=crop",
        "Project" to "https://images.unsplash.com/photo-1552664730-d307ca884978?w=200&h=200&fit=crop",
        "Study" to "drawable://emoji_thinking_monocle",
        "Exam" to "drawable://emoji_thinking_monocle",
        "Research" to "drawable://emoji_microscope",
        "Gaming" to "https://images.unsplash.com/photo-1542751371-adc38448a05e?w=200&h=200&fit=crop",
        "Sports" to "https://images.unsplash.com/photo-1461896836934-ffe607ba8211?w=200&h=200&fit=crop",
        "Music" to "https://images.unsplash.com/photo-1493225457124-a3eb161ffa5f?w=200&h=200&fit=crop",
        "Travel" to "https://images.unsplash.com/photo-1488646953014-85cb44e25828?w=200&h=200&fit=crop",
        "Food" to "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=200&h=200&fit=crop",
        "Fitness" to "https://images.unsplash.com/photo-1571019613454-1cb2f99b2d8b?w=200&h=200&fit=crop"
    )
    
    // Default group avatar
    private val defaultGroupAvatar = "https://images.unsplash.com/photo-1529156069898-49953e39b3ac?w=200&h=200&fit=crop"
    
    fun generateUsers(count: Int = 100): List<UserEntity> {
        return (1..count).map { index ->
            // Use deterministic selection based on index to avoid randomization
            val firstName = firstNames[index % firstNames.size]
            val lastName = lastNames[index % lastNames.size]
            val userNum = 100 + index // Deterministic user number
            val username = "${firstName.lowercase()}.${lastName.lowercase()}$userNum"
            val isOnline = index % 3 == 0 // Every 3rd user is online (deterministic)
            
            // Use modulo to cycle through available avatars
            val avatarUrl = avatarUrls[index % avatarUrls.size]
            
            UserEntity(
                userId = "user_$index",
                username = username,
                displayName = "$firstName $lastName",
                avatarUrl = avatarUrl,
                isOnline = isOnline,
                lastSeen = if (isOnline) {
                    System.currentTimeMillis()
                } else {
                    System.currentTimeMillis() - (3600000L * index) // Deterministic: 1 hour per index
                },
                statusMessage = statusMessages[index % statusMessages.size]
            )
        }
    }
    
    fun generateConversations(users: List<UserEntity>, currentUserId: String = "user_1"): List<ConversationEntity> {
        val conversations = mutableListOf<ConversationEntity>()
        val usedGroupNames = mutableSetOf<String>()
        
        // Generate 50 conversations
        for (i in 1..50) {
            val isGroup = i % 3 == 0 // Every 3rd conversation is a group (deterministic)
            val conversationId = "conv_$i"
            
            // Set specific lastViewedAt times for certain conversations to control unread counts
            // Make sure top conversations have unread messages
            val lastViewedAt = when (i) {
                1 -> System.currentTimeMillis() - 120000   // 2 minutes ago (will have ~2 unread)
                2 -> System.currentTimeMillis() - 300000  // 5 minutes ago (will have ~4-5 unread)
                3 -> System.currentTimeMillis() - 900000  // 15 minutes ago (will have many unread)
                // A few more scattered throughout for realism
                7 -> System.currentTimeMillis() - 60000   // 1 minute ago (will have 1 unread)
                12 -> System.currentTimeMillis() - 180000  // 3 minutes ago (will have ~3 unread)
                else -> {
                    // For other conversations, make them viewed recently (no unread)
                    System.currentTimeMillis() - (30000L * i) // 30 seconds per index
                }
            }
            
            val conversation = if (isGroup) {
                val participantCount = 3 + (i % 5) // Deterministic: 3-7 participants
                var groupName = if (i == 6) "Best Friends" else generateGroupName(i) // Add Best Friends as conversation 6
                
                // Ensure unique group names by adding a suffix if needed
                var suffix = 1
                val baseName = groupName
                while (usedGroupNames.contains(groupName)) {
                    suffix++
                    groupName = "$baseName $suffix"
                }
                usedGroupNames.add(groupName)
                
                ConversationEntity(
                    conversationId = conversationId,
                    title = groupName,
                    isGroup = true,
                    createdAt = System.currentTimeMillis() - (3600000L * 2), // Created 2 hours ago
                    updatedAt = System.currentTimeMillis() - (60000L * i), // Updated minutes ago based on position
                    unreadCount = 0, // Will be calculated based on lastViewedAt
                    isPinned = false, // No conversations are pinned for now
                    isMuted = i % 10 == 0, // Every 10th conversation is muted (deterministic)
                    avatarUrl = generateGroupAvatarUrl(groupName, i),
                    lastViewedAt = lastViewedAt
                )
            } else {
                ConversationEntity(
                    conversationId = conversationId,
                    title = null,
                    isGroup = false,
                    createdAt = System.currentTimeMillis() - (3600000L * 2), // Created 2 hours ago
                    updatedAt = System.currentTimeMillis() - (60000L * i), // Updated minutes ago based on position
                    unreadCount = 0, // Will be calculated based on lastViewedAt
                    isPinned = false, // No conversations are pinned for now
                    isMuted = i % 10 == 0, // Every 10th conversation is muted (deterministic)
                    avatarUrl = null,
                    lastViewedAt = lastViewedAt
                )
            }
            
            conversations.add(conversation)
        }
        
        return conversations
    }
    
    fun generateConversationParticipants(
        conversations: List<ConversationEntity>,
        users: List<UserEntity>,
        currentUserId: String = "user_1"
    ): List<ConversationParticipantEntity> {
        val participants = mutableListOf<ConversationParticipantEntity>()
        val availableUsers = users.filter { it.userId != currentUserId }.toMutableList()
        var userIndex = 0  // Track which user to assign to individual chats
        
        conversations.forEach { conversation ->
            val conversationIndex = conversation.conversationId.substringAfter("conv_").toIntOrNull() ?: 0
            
            // Always add current user to conversation
            participants.add(
                ConversationParticipantEntity(
                    conversationId = conversation.conversationId,
                    userId = currentUserId,
                    role = if (conversationIndex % 3 == 0) ParticipantRole.ADMIN else ParticipantRole.MEMBER
                )
            )
            
            if (conversation.isGroup) {
                // Add deterministic number of participants for group chats
                val participantCount = 2 + (conversationIndex % 6) // 2-7 participants
                val selectedUsers = availableUsers.drop(conversationIndex).take(participantCount)
                
                selectedUsers.forEachIndexed { index, user ->
                    participants.add(
                        ConversationParticipantEntity(
                            conversationId = conversation.conversationId,
                            userId = user.userId,
                            role = when {
                                index == 0 -> ParticipantRole.ADMIN
                                index == 1 -> ParticipantRole.MODERATOR
                                else -> ParticipantRole.MEMBER
                            },
                            joinedAt = conversation.createdAt + (3600000L * index) // Deterministic join time
                        )
                    )
                }
            } else {
                // Add 1 other participant for direct messages
                // Use deterministic assignment based on conversation index
                // This ensures conv_1 gets user_2, conv_2 gets user_3, etc.
                val otherUserIndex = conversationIndex - 1 // conv_1 -> index 0 -> user_2
                if (otherUserIndex < availableUsers.size) {
                    val otherUser = availableUsers[otherUserIndex]
                    participants.add(
                        ConversationParticipantEntity(
                            conversationId = conversation.conversationId,
                            userId = otherUser.userId,
                            role = ParticipantRole.MEMBER
                        )
                    )
                }
            }
        }
        
        return participants
    }
    
    // Add mentions for group chats
    private fun addMentionIfGroup(message: String, isGroup: Boolean, participants: List<ConversationParticipantEntity>): String {
        if (!isGroup || participants.isEmpty()) return message
        
        // Occasionally add @mentions (20% chance)
        if ((message.hashCode() % 5) == 0) {
            val randomParticipant = participants[message.length % participants.size]
            val userName = "User${randomParticipant.userId.substringAfter("user_")}"
            return "@$userName $message"
        }
        return message
    }
    
    // Generate contextually appropriate message content based on group name
    data class ConversationMessage(
        val content: String,
        val isFromCurrentUser: Boolean,
        val type: MessageType = MessageType.TEXT
    )
    
    private fun generateConversationFlow(conversationIndex: Int, messageCount: Int): List<ConversationMessage> {
        // Use conversation index to create unique variations
        val variation = conversationIndex % 20
        val timeOfDay = when ((conversationIndex + variation) % 4) {
            0 -> "morning"
            1 -> "afternoon" 
            2 -> "evening"
            else -> "night"
        }
        
        val flows = listOf(
            // Flow 1: Making lunch plans
            listOf(
                ConversationMessage("Hey! You free for lunch today?", true),
                ConversationMessage("Yeah! What time were you thinking?", false),
                ConversationMessage("How about 12:30?", true),
                ConversationMessage("Perfect! Where do you want to go?", false),
                ConversationMessage("That new Thai place downtown?", true),
                ConversationMessage("Ooh yes! I've been wanting to try it", false),
                ConversationMessage("Great! Meet you in the lobby?", true),
                ConversationMessage("Sounds good! See you then 👍", false),
                ConversationMessage("Actually, can we make it 1pm instead?", false),
                ConversationMessage("Sure, no problem!", true),
                ConversationMessage("Thanks! Got a meeting running late", false),
                ConversationMessage("No worries, happens to me all the time", true)
            ),
            
            // Flow 2: Work project discussion
            listOf(
                ConversationMessage("Quick question about the project", false),
                ConversationMessage("Sure, what's up?", true),
                ConversationMessage("Did you finish the API integration?", false),
                ConversationMessage("Almost done! Just debugging the auth flow", true),
                ConversationMessage("Great! Let me know if you need help", false),
                ConversationMessage("Actually, could you look at this error?", true),
                ConversationMessage("Send me the logs", false),
                ConversationMessage("error_log.txt", true, MessageType.FILE),
                ConversationMessage("Ah I see the issue", false),
                ConversationMessage("It's a token expiration problem", false),
                ConversationMessage("Oh that makes sense!", true),
                ConversationMessage("Try refreshing before each request", false),
                ConversationMessage("Will do, thanks!", true)
            ),
            
            // Flow 3: Weekend plans
            listOf(
                ConversationMessage("Any plans for the weekend?", false),
                ConversationMessage("Not really, you?", true),
                ConversationMessage("Thinking about hiking if the weather's nice", false),
                ConversationMessage("That sounds fun! Which trail?", true),
                ConversationMessage("Eagle Peak, heard it has amazing views", false),
                ConversationMessage("I've been there! It's beautiful", true),
                ConversationMessage("Really? How difficult is it?", false),
                ConversationMessage("Moderate, took me about 3 hours", true),
                ConversationMessage("Perfect! Want to join?", false),
                ConversationMessage("I'd love to! What time?", true),
                ConversationMessage("Early, like 7am? Beat the crowds", false),
                ConversationMessage("Works for me! I'll bring snacks", true),
                ConversationMessage("Awesome! Can't wait", false)
            ),
            
            // Flow 4: Sharing exciting news
            listOf(
                ConversationMessage("OMG guess what!!!", true),
                ConversationMessage("What?? Tell me!", false),
                ConversationMessage("I GOT THE JOB!!! 🎉", true),
                ConversationMessage("NO WAY!! CONGRATULATIONS!!!", false),
                ConversationMessage("Thank you!! I'm still shaking", true),
                ConversationMessage("When do you start?", false),
                ConversationMessage("Next month! Better salary too", true),
                ConversationMessage("This is amazing! So proud of you!", false),
                ConversationMessage("Couldn't have done it without your help", true),
                ConversationMessage("Stop it, this was all you!", false),
                ConversationMessage("Seriously though, thank you for everything", true),
                ConversationMessage("We need to celebrate! Drinks tonight?", false),
                ConversationMessage("Absolutely! First round's on me!", true)
            ),
            
            // Flow 5: Morning coffee run
            listOf(
                ConversationMessage("Coffee run?", true),
                ConversationMessage("YES PLEASE", false),
                ConversationMessage("Usual?", true),
                ConversationMessage("Actually can you make it iced today?", false),
                ConversationMessage("Sure! Same size?", true),
                ConversationMessage("Large please, rough morning", false),
                ConversationMessage("😅 I feel you", true),
                ConversationMessage("Want anything else?", true),
                ConversationMessage("Maybe a croissant if they have any", false),
                ConversationMessage("Will check! Be back in 15", true),
                ConversationMessage("You're the best! ☕", false)
            ),
            
            // Flow 6: Car trouble help
            listOf(
                ConversationMessage("Hey, you around?", false),
                ConversationMessage("Yeah, what's up?", true),
                ConversationMessage("My car won't start 😫", false),
                ConversationMessage("Oh no! Where are you?", true),
                ConversationMessage("Office parking lot", false),
                ConversationMessage("I can come jump it. Have cables?", true),
                ConversationMessage("No... sorry", false),
                ConversationMessage("No worries, I have some. Be there in 10", true),
                ConversationMessage("Thank you so much!! Lifesaver", false),
                ConversationMessage("On my way now", true),
                ConversationMessage("I owe you big time", false),
                ConversationMessage("Just pay it forward 😊", true)
            ),
            
            // Flow 7: Movie night planning
            listOf(
                ConversationMessage("Movie night at mine tonight?", false),
                ConversationMessage("Sure! What are we watching?", true),
                ConversationMessage("Haven't decided. Any preferences?", false),
                ConversationMessage("Something funny? Need to decompress", true),
                ConversationMessage("Perfect! I have a few comedies in mind", false),
                ConversationMessage("What time should I come over?", true),
                ConversationMessage("7:30? Give us time for dinner first", false),
                ConversationMessage("Sounds good! Want me to bring anything?", true),
                ConversationMessage("Just yourself! I've got snacks covered", false),
                ConversationMessage("You sure? I can grab pizza", true),
                ConversationMessage("Actually... pizza sounds amazing", false),
                ConversationMessage("On it! The usual place?", true),
                ConversationMessage("Yes! You know me so well", false)
            ),
            
            // Flow 8: Quick favor
            listOf(
                ConversationMessage("Quick favor?", true),
                ConversationMessage("Sure, what do you need?", false),
                ConversationMessage("Can you send me Sarah's number?", true),
                ConversationMessage("The Sarah from marketing?", false),
                ConversationMessage("Yeah, need to ask about the budget", true),
                ConversationMessage("One sec, let me find it", false),
                ConversationMessage("555-0123", false),
                ConversationMessage("Perfect, thanks!", true),
                ConversationMessage("No problem! Tell her I said hi", false),
                ConversationMessage("Will do!", true)
            ),
            
            // Flow 9: Shopping/errands
            listOf(
                ConversationMessage("Hey, heading to the store", false),
                ConversationMessage("Need anything?", false),
                ConversationMessage("Oh perfect timing!", true),
                ConversationMessage("Can you grab some milk?", true),
                ConversationMessage("Sure! 2% or whole?", false),
                ConversationMessage("2% please", true),
                ConversationMessage("Got it. Anything else?", false),
                ConversationMessage("Maybe some bread if they have the good kind", true),
                ConversationMessage("The sourdough one?", false),
                ConversationMessage("Yes! You know me so well", true),
                ConversationMessage("Haha of course", false),
                ConversationMessage("Thanks so much!", true)
            ),
            
            // Flow 10: Tech support
            listOf(
                ConversationMessage("My laptop is being weird", true),
                ConversationMessage("What's it doing?", false),
                ConversationMessage("It's super slow all of a sudden", true),
                ConversationMessage("Have you tried restarting?", false),
                ConversationMessage("Yeah, twice", true),
                ConversationMessage("Check if any app is using too much CPU", false),
                ConversationMessage("How do I do that?", true),
                ConversationMessage("Open Activity Monitor", false),
                ConversationMessage("Oh wow, Chrome is using 90%", true),
                ConversationMessage("Classic Chrome lol", false),
                ConversationMessage("Killed it, much better now!", true),
                ConversationMessage("Chrome strikes again 😄", false)
            ),
            
            // Flow 11: Birthday planning
            listOf(
                ConversationMessage("Don't forget Mom's birthday is next week", false),
                ConversationMessage("Oh right! What should we get her?", true),
                ConversationMessage("I was thinking that bracelet she liked", false),
                ConversationMessage("The silver one?", true),
                ConversationMessage("Yeah, from that store downtown", false),
                ConversationMessage("Perfect! Want to split it?", true),
                ConversationMessage("Sounds good", false),
                ConversationMessage("I can pick it up tomorrow", true),
                ConversationMessage("Thanks! Let me know how much", false),
                ConversationMessage("Will do!", true)
            ),
            
            // Flow 12: Gym/workout
            listOf(
                ConversationMessage("Gym tonight?", true),
                ConversationMessage("What time?", false),
                ConversationMessage("7pm?", true),
                ConversationMessage("Perfect, leg day?", false),
                ConversationMessage("Ugh yes, unfortunately", true),
                ConversationMessage("RIP our legs tomorrow 😂", false),
                ConversationMessage("Worth it though!", true),
                ConversationMessage("True! See you there", false),
                ConversationMessage("Don't forget your water bottle this time", true),
                ConversationMessage("Good reminder lol", false)
            ),
            
            // Flow 13: Restaurant recommendation
            listOf(
                ConversationMessage("Know any good Italian places?", false),
                ConversationMessage("Oh yeah! There's this amazing spot", true),
                ConversationMessage("Called Giuseppe's on Main", true),
                ConversationMessage("Is it expensive?", false),
                ConversationMessage("Pretty reasonable actually", true),
                ConversationMessage("Their pasta is incredible", true),
                ConversationMessage("Sounds perfect!", false),
                ConversationMessage("Going on a date?", true),
                ConversationMessage("Maybe... 😊", false),
                ConversationMessage("Ooh exciting! Have fun!", true),
                ConversationMessage("Thanks! I'll let you know how it goes", false)
            ),
            
            // Flow 14: Pet stories
            listOf(
                ConversationMessage("My cat did the funniest thing today", true),
                ConversationMessage("What now? 😂", false),
                ConversationMessage("She got stuck in a paper bag", true),
                ConversationMessage("Classic cat move", false),
                ConversationMessage("She was running around with it on her head", true),
                ConversationMessage("Please tell me you got a video", false),
                ConversationMessage("Of course!", true),
                ConversationMessage("cat_bag_adventure.mp4", true, MessageType.VIDEO),
                ConversationMessage("HAHAHA that's hilarious!", false),
                ConversationMessage("She's such a goofball", true),
                ConversationMessage("Cats are the best entertainment", false)
            ),
            
            // Flow 15: Travel planning
            listOf(
                ConversationMessage("Flights to Miami are super cheap right now", false),
                ConversationMessage("How cheap?", true),
                ConversationMessage("Like $150 round trip", false),
                ConversationMessage("Whoa! When?", true),
                ConversationMessage("Next month, weekdays though", false),
                ConversationMessage("I could probably take time off", true),
                ConversationMessage("Want to go together?", false),
                ConversationMessage("That would be amazing!", true),
                ConversationMessage("Beach vacation here we come!", false),
                ConversationMessage("I'll check my calendar", true),
                ConversationMessage("Let me know! Sale ends tomorrow", false)
            )
        )
        
        // Create unique combinations by mixing flows
        val baseFlowIndex = conversationIndex % flows.size
        val secondaryFlowIndex = (conversationIndex + 7) % flows.size
        
        // Select primary flow
        val selectedFlow = flows[baseFlowIndex]
        
        // If we need more messages than the flow provides, add contextual variations
        val result = mutableListOf<ConversationMessage>()
        var messageIndex = 0
        
        // Add the main flow
        while (messageIndex < selectedFlow.size && result.size < messageCount) {
            result.add(selectedFlow[messageIndex])
            messageIndex++
        }
        
        // If we need more messages, add contextual follow-ups based on conversation type
        if (result.size < messageCount) {
            val continuationMessages = generateContinuationMessages(conversationIndex, baseFlowIndex)
            
            // Add continuation messages without repetition
            var contIndex = 0
            while (result.size < messageCount && contIndex < continuationMessages.size) {
                result.add(continuationMessages[contIndex])
                contIndex++
            }
            
            // If we still need more messages, generate final closing messages
            if (result.size < messageCount) {
                val remainingCount = messageCount - result.size
                val closingMessages = generateClosingMessages(conversationIndex, remainingCount)
                result.addAll(closingMessages)
            }
        }
        
        return result.take(messageCount)
    }
    
    private fun generateContinuationMessages(conversationIndex: Int, flowType: Int): List<ConversationMessage> {
        // Create a large pool of diverse continuation messages
        val allContinuations = mutableListOf<ConversationMessage>()
        
        // Mix different conversation topics based on context
        val topics = listOf(
            // Topic transitions
            listOf(
                ConversationMessage("Oh before I forget", conversationIndex % 2 == 0),
                ConversationMessage("What's up?", conversationIndex % 2 != 0),
                ConversationMessage("Can you do me a favor tomorrow?", conversationIndex % 2 == 0),
                ConversationMessage("Sure, what do you need?", conversationIndex % 2 != 0),
                ConversationMessage("Just need a ride to pick up my car", conversationIndex % 2 == 0),
                ConversationMessage("No problem, what time?", conversationIndex % 2 != 0),
                ConversationMessage("Around 10am work?", conversationIndex % 2 == 0),
                ConversationMessage("Perfect!", conversationIndex % 2 != 0)
            ),
            
            // News sharing
            listOf(
                ConversationMessage("Did you hear about what happened?", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("No, what?", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("They're closing that store we like", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("What?! No way!", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("Yeah, end of the month", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("That's so sad", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("I know right", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("We should go one last time", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("Definitely", (conversationIndex + 1) % 2 == 0)
            ),
            
            // Random life updates
            listOf(
                ConversationMessage("So tired today", conversationIndex % 2 == 0),
                ConversationMessage("Late night?", conversationIndex % 2 != 0),
                ConversationMessage("Couldn't sleep", conversationIndex % 2 == 0),
                ConversationMessage("Hate when that happens", conversationIndex % 2 != 0),
                ConversationMessage("Tried everything", conversationIndex % 2 == 0),
                ConversationMessage("Maybe try melatonin?", conversationIndex % 2 != 0),
                ConversationMessage("Good idea", conversationIndex % 2 == 0),
                ConversationMessage("Works for me", conversationIndex % 2 != 0)
            ),
            
            // Making spontaneous plans
            listOf(
                ConversationMessage("You busy right now?", (conversationIndex + 2) % 2 == 0),
                ConversationMessage("Not really, why?", (conversationIndex + 2) % 2 != 0),
                ConversationMessage("Want to grab a quick bite?", (conversationIndex + 2) % 2 == 0),
                ConversationMessage("Actually yeah, I'm hungry", (conversationIndex + 2) % 2 != 0),
                ConversationMessage("Cool, where?", (conversationIndex + 2) % 2 == 0),
                ConversationMessage("That taco place?", (conversationIndex + 2) % 2 != 0),
                ConversationMessage("Perfect, leaving now", (conversationIndex + 2) % 2 == 0),
                ConversationMessage("See you in 10", (conversationIndex + 2) % 2 != 0)
            ),
            
            // Weather chat
            listOf(
                ConversationMessage("This weather is crazy", conversationIndex % 2 == 0),
                ConversationMessage("I know! So random", conversationIndex % 2 != 0),
                ConversationMessage("Supposed to rain all week", conversationIndex % 2 == 0),
                ConversationMessage("Ugh great", conversationIndex % 2 != 0),
                ConversationMessage("At least it's not snow", conversationIndex % 2 == 0),
                ConversationMessage("True", conversationIndex % 2 != 0),
                ConversationMessage("Still annoying though", conversationIndex % 2 == 0),
                ConversationMessage("Very", conversationIndex % 2 != 0)
            ),
            
            // TV/Movie discussion
            listOf(
                ConversationMessage("Started any new shows lately?", (conversationIndex + 3) % 2 == 0),
                ConversationMessage("Actually yeah!", (conversationIndex + 3) % 2 != 0),
                ConversationMessage("What are you watching?", (conversationIndex + 3) % 2 == 0),
                ConversationMessage("That new thriller everyone's talking about", (conversationIndex + 3) % 2 != 0),
                ConversationMessage("Is it good?", (conversationIndex + 3) % 2 == 0),
                ConversationMessage("SO good", (conversationIndex + 3) % 2 != 0),
                ConversationMessage("Worth the hype?", (conversationIndex + 3) % 2 == 0),
                ConversationMessage("Definitely", (conversationIndex + 3) % 2 != 0),
                ConversationMessage("Adding it to my list", (conversationIndex + 3) % 2 == 0)
            ),
            
            // Food recommendations
            listOf(
                ConversationMessage("I'm so hungry", conversationIndex % 2 == 0),
                ConversationMessage("What are you craving?", conversationIndex % 2 != 0),
                ConversationMessage("Something spicy", conversationIndex % 2 == 0),
                ConversationMessage("Indian food?", conversationIndex % 2 != 0),
                ConversationMessage("Ooh that sounds good", conversationIndex % 2 == 0),
                ConversationMessage("That place on 5th is great", conversationIndex % 2 != 0),
                ConversationMessage("Never been", conversationIndex % 2 == 0),
                ConversationMessage("You have to try it", conversationIndex % 2 != 0),
                ConversationMessage("Maybe I'll order from there", conversationIndex % 2 == 0),
                ConversationMessage("Get the butter chicken!", conversationIndex % 2 != 0)
            ),
            
            // Weekend recap
            listOf(
                ConversationMessage("How was your weekend?", (conversationIndex + 4) % 2 == 0),
                ConversationMessage("Really good actually", (conversationIndex + 4) % 2 != 0),
                ConversationMessage("What did you do?", (conversationIndex + 4) % 2 == 0),
                ConversationMessage("Went hiking on Saturday", (conversationIndex + 4) % 2 != 0),
                ConversationMessage("Nice! Where?", (conversationIndex + 4) % 2 == 0),
                ConversationMessage("That trail by the lake", (conversationIndex + 4) % 2 != 0),
                ConversationMessage("Love that spot", (conversationIndex + 4) % 2 == 0),
                ConversationMessage("The views were amazing", (conversationIndex + 4) % 2 != 0),
                ConversationMessage("I need to go back there", (conversationIndex + 4) % 2 == 0),
                ConversationMessage("We should go together next time", (conversationIndex + 4) % 2 != 0),
                ConversationMessage("I'm down!", (conversationIndex + 4) % 2 == 0)
            )
        )
        
        // Select multiple topics to create a longer, varied conversation
        val selectedTopics = mutableListOf<List<ConversationMessage>>()
        
        // Pick 2-3 different topics based on conversation index
        val topicCount = 2 + (conversationIndex % 2)
        for (i in 0 until topicCount) {
            val topicIndex = (conversationIndex + i * 3) % topics.size
            selectedTopics.add(topics[topicIndex])
        }
        
        // Combine topics with natural transitions
        selectedTopics.forEach { topic ->
            allContinuations.addAll(topic)
            
            // Add a natural transition between topics
            if (selectedTopics.indexOf(topic) < selectedTopics.size - 1) {
                val transitions = listOf(
                    ConversationMessage("Oh also", conversationIndex % 2 == 0),
                    ConversationMessage("By the way", conversationIndex % 2 != 0),
                    ConversationMessage("Speaking of which", conversationIndex % 2 == 0),
                    ConversationMessage("Random question", conversationIndex % 2 != 0),
                    ConversationMessage("Totally different topic but", conversationIndex % 2 == 0)
                )
                allContinuations.add(transitions[kotlin.math.abs(conversationIndex + topic.hashCode()) % transitions.size])
            }
        }
        
        // Add a natural ending
        val endings = listOf(
            listOf(
                ConversationMessage("Alright, I should go", conversationIndex % 2 == 0),
                ConversationMessage("Yeah me too", conversationIndex % 2 != 0),
                ConversationMessage("Talk later!", conversationIndex % 2 == 0),
                ConversationMessage("For sure! Bye!", conversationIndex % 2 != 0)
            ),
            listOf(
                ConversationMessage("Getting late", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("Yeah, should sleep", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("Night!", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("Sweet dreams!", (conversationIndex + 1) % 2 != 0)
            ),
            listOf(
                ConversationMessage("Gotta get back to work", conversationIndex % 2 == 0),
                ConversationMessage("Same, talk soon", conversationIndex % 2 != 0),
                ConversationMessage("👍", conversationIndex % 2 == 0)
            )
        )
        
        allContinuations.addAll(endings[conversationIndex % endings.size])
        
        return allContinuations
    }
    
    private fun generateUnreadContinuation(
        conversation: ConversationEntity,
        otherParticipant: ConversationParticipantEntity,
        unreadCount: Int,
        conversationIndex: Int,
        previousMessageId: String?
    ): List<MessageEntity> {
        val messages = mutableListOf<MessageEntity>()
        // Set base time to be AFTER lastViewedAt to ensure these messages are unread
        val baseTime = conversation.lastViewedAt + 60000L // Start 1 minute after lastViewedAt
        
        // Generate contextual unread messages based on conversation index
        val unreadContents = when (conversationIndex) {
            1 -> listOf(
                "Are you still there?",
                "Let me know when you get this"
            )
            2 -> listOf(
                "I forgot to mention",
                "The meeting got moved to 3pm",
                "Can you make it?",
                "Also bring the reports",
                "Thanks!"
            )
            7 -> listOf(
                "Never mind, figured it out!"
            )
            else -> {
                // Generate generic follow-ups
                val genericFollowUps = listOf(
                    listOf("Hey", "You there?", "Hello??"),
                    listOf("Quick update", "Things changed", "Call me when you can"),
                    listOf("One more thing", "Almost forgot", "Important!")
                )
                genericFollowUps[conversationIndex % genericFollowUps.size].take(unreadCount)
            }
        }
        
        // Create the unread messages
        unreadContents.take(unreadCount).forEachIndexed { index, content ->
            val messageId = UUID.randomUUID().toString()
            val timestamp = baseTime + (index * 60000L) // 1 minute apart
            
            messages.add(
                MessageEntity(
                    messageId = messageId,
                    conversationId = conversation.conversationId,
                    senderId = otherParticipant.userId,
                    content = content,
                    timestamp = timestamp,
                    messageType = MessageType.TEXT,
                    isRead = false, // These are unread
                    isDelivered = true,
                    replyToMessageId = if (index == 0 && previousMessageId != null) {
                        previousMessageId
                    } else null
                )
            )
        }
        
        return messages
    }
    
    private fun generateGroupUnreadContinuation(
        conversation: ConversationEntity,
        otherParticipants: List<ConversationParticipantEntity>,
        unreadCount: Int,
        conversationIndex: Int,
        previousMessageId: String?
    ): List<MessageEntity> {
        val messages = mutableListOf<MessageEntity>()
        // Set base time to be AFTER lastViewedAt to ensure these messages are unread
        val baseTime = conversation.lastViewedAt + 60000L // Start 1 minute after lastViewedAt
        val groupName = conversation.title ?: "Group"
        
        // Generate contextual unread messages for groups
        val unreadFlows = when {
            groupName.contains("Family") -> listOf(
                Pair(0, "Anyone home?"),
                Pair(1, "I'm bringing pizza"),
                Pair(0, "Get the regular order?"),
                Pair(2, "Yes please!"),
                Pair(1, "On my way")
            )
            groupName.contains("Marketing") || groupName.contains("Team") -> listOf(
                Pair(0, "Meeting in 5"),
                Pair(1, "Be right there"),
                Pair(2, "Can someone share the deck?"),
                Pair(0, "Just sent it"),
                Pair(1, "Got it, thanks")
            )
            groupName.contains("Friends") -> listOf(
                Pair(0, "Who's coming tonight?"),
                Pair(1, "I'm in!"),
                Pair(2, "Me too"),
                Pair(0, "Great, see you at 8"),
                Pair(1, "Should I bring anything?")
            )
            groupName.contains("Study") || groupName.contains("Class") || groupName.contains("Exam") -> listOf(
                Pair(0, "Did anyone finish problem 5?"),
                Pair(1, "Still working on it"),
                Pair(2, "The answer is 42"),
                Pair(0, "How did you get that?"),
                Pair(1, "Check the formula sheet")
            )
            groupName.contains("Gaming") -> listOf(
                Pair(0, "Anyone online?"),
                Pair(1, "Loading in now"),
                Pair(2, "Start without me, 5 mins"),
                Pair(0, "Lobby code: ABC123"),
                Pair(1, "Joining!")
            )
            groupName.contains("Fitness") || groupName.contains("Gym") || groupName.contains("Yoga") -> listOf(
                Pair(0, "Who's coming to class?"),
                Pair(1, "I'll be there"),
                Pair(2, "Running 10 mins late"),
                Pair(0, "Save you a spot"),
                Pair(1, "Thanks!")
            )
            else -> {
                // More natural generic group messages
                listOf(
                    Pair(0, "Hey everyone"),
                    Pair(1, "What's up?"),
                    Pair(2, "Not much, you?"),
                    Pair(0, "Just checking in"),
                    Pair(1, "All good here")
                )
            }
        }
        
        // Create the unread messages
        unreadFlows.take(unreadCount).forEachIndexed { index, (participantIndex, content) ->
            val messageId = UUID.randomUUID().toString()
            val timestamp = baseTime + (index * 45000L) // 45 seconds apart
            val sender = otherParticipants[participantIndex % otherParticipants.size]
            
            messages.add(
                MessageEntity(
                    messageId = messageId,
                    conversationId = conversation.conversationId,
                    senderId = sender.userId,
                    content = content,
                    timestamp = timestamp,
                    messageType = MessageType.TEXT,
                    isRead = false, // These are unread
                    isDelivered = true,
                    replyToMessageId = if (index == 0 && previousMessageId != null && index % 3 == 0) {
                        previousMessageId
                    } else null
                )
            )
        }
        
        return messages
    }
    
    private fun generateClosingMessages(conversationIndex: Int, count: Int): List<ConversationMessage> {
        val closingMessages = mutableListOf<ConversationMessage>()
        val variation = conversationIndex % 5
        
        // Generate unique closing messages based on count needed
        val possibleClosings = listOf(
            // Quick back-and-forth
            listOf(
                ConversationMessage("Sounds good", conversationIndex % 2 == 0),
                ConversationMessage("👍", conversationIndex % 2 != 0),
                ConversationMessage("Cool", conversationIndex % 2 == 0),
                ConversationMessage("Yep", conversationIndex % 2 != 0),
                ConversationMessage("Alright", conversationIndex % 2 == 0),
                ConversationMessage("K", conversationIndex % 2 != 0),
                ConversationMessage("Later!", conversationIndex % 2 == 0),
                ConversationMessage("Bye!", conversationIndex % 2 != 0)
            ),
            
            // Confirming details
            listOf(
                ConversationMessage("So tomorrow then?", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("Yes, tomorrow", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("Same time?", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("Works for me", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("Great", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("See you then", (conversationIndex + 1) % 2 != 0),
                ConversationMessage("Looking forward to it", (conversationIndex + 1) % 2 == 0),
                ConversationMessage("Me too!", (conversationIndex + 1) % 2 != 0)
            ),
            
            // Wrapping up
            listOf(
                ConversationMessage("I should probably go", conversationIndex % 2 == 0),
                ConversationMessage("Yeah, getting late", conversationIndex % 2 != 0),
                ConversationMessage("Thanks for chatting", conversationIndex % 2 == 0),
                ConversationMessage("Always good talking to you", conversationIndex % 2 != 0),
                ConversationMessage("Same!", conversationIndex % 2 == 0),
                ConversationMessage("Have a good night", conversationIndex % 2 != 0),
                ConversationMessage("You too", conversationIndex % 2 == 0),
                ConversationMessage("😊", conversationIndex % 2 != 0)
            ),
            
            // Quick exchanges
            listOf(
                ConversationMessage("Ok", (conversationIndex + 2) % 2 == 0),
                ConversationMessage("Cool", (conversationIndex + 2) % 2 != 0),
                ConversationMessage("Thanks", (conversationIndex + 2) % 2 == 0),
                ConversationMessage("No prob", (conversationIndex + 2) % 2 != 0),
                ConversationMessage("ttyl", (conversationIndex + 2) % 2 == 0),
                ConversationMessage("Later", (conversationIndex + 2) % 2 != 0),
                ConversationMessage("✌️", (conversationIndex + 2) % 2 == 0)
            ),
            
            // Final thoughts
            listOf(
                ConversationMessage("Oh one more thing", conversationIndex % 2 == 0),
                ConversationMessage("Yeah?", conversationIndex % 2 != 0),
                ConversationMessage("Never mind, I'll tell you later", conversationIndex % 2 == 0),
                ConversationMessage("Now I'm curious lol", conversationIndex % 2 != 0),
                ConversationMessage("It's nothing important", conversationIndex % 2 == 0),
                ConversationMessage("If you say so", conversationIndex % 2 != 0),
                ConversationMessage("Haha yeah", conversationIndex % 2 == 0),
                ConversationMessage("Alright, bye for real", conversationIndex % 2 != 0),
                ConversationMessage("Bye! 😄", conversationIndex % 2 == 0)
            )
        )
        
        val selectedClosing = possibleClosings[variation]
        
        // Take only as many messages as needed
        for (i in 0 until minOf(count, selectedClosing.size)) {
            closingMessages.add(selectedClosing[i])
        }
        
        // If we still need more, add some generic endings
        if (closingMessages.size < count) {
            val genericEndings = listOf(
                ConversationMessage("...", conversationIndex % 2 == 0),
                ConversationMessage("?", conversationIndex % 2 != 0),
                ConversationMessage("Anyway", conversationIndex % 2 == 0),
                ConversationMessage("Yeah", conversationIndex % 2 != 0),
                ConversationMessage("So", conversationIndex % 2 == 0),
                ConversationMessage("Hmm", conversationIndex % 2 != 0)
            )
            
            var genericIndex = 0
            while (closingMessages.size < count && genericIndex < genericEndings.size) {
                closingMessages.add(genericEndings[genericIndex])
                genericIndex++
            }
        }
        
        return closingMessages
    }
    
    private fun generateGroupConversationFlow(
        groupName: String,
        participants: List<ConversationParticipantEntity>,
        messageCount: Int,
        conversationIndex: Int,
        conversation: ConversationEntity,
        guaranteedUnreadCount: Int
    ): List<MessageEntity> {
        val messages = mutableListOf<MessageEntity>()
        val currentUserId = "user_1"
        val conversationId = "conv_$conversationIndex"
        
        // Get participant IDs excluding current user for realistic group dynamics
        val otherParticipants = participants.filter { it.userId != currentUserId }
        if (otherParticipants.isEmpty()) return messages
        
        // Select appropriate conversation flow based on group name
        val initialGroupFlow = when {
            groupName == "Best Friends" -> generateBestFriendsFlow(otherParticipants, currentUserId)
            groupName.contains("Family") -> generateFamilyGroupFlow(otherParticipants, currentUserId)
            groupName.contains("Work") || groupName.contains("Team") || groupName.contains("Project") -> 
                generateWorkGroupFlow(groupName, otherParticipants, currentUserId)
            groupName.contains("Friends") || groupName.contains("Squad") -> 
                generateFriendsGroupFlow(groupName, otherParticipants, currentUserId)
            groupName.contains("Study") || groupName.contains("Class") || groupName.contains("Exam") -> 
                generateStudyGroupFlow(otherParticipants, currentUserId)
            groupName.contains("Gaming") -> generateGamingGroupFlow(otherParticipants, currentUserId)
            groupName.contains("Fitness") || groupName.contains("Gym") || groupName.contains("Yoga") || 
            groupName.contains("Tennis") || groupName.contains("Running") -> 
                generateFitnessGroupFlow(groupName, otherParticipants, currentUserId)
            groupName.contains("Food") || groupName.contains("Recipe") || groupName.contains("Cook") -> 
                generateFoodGroupFlow(otherParticipants, currentUserId)
            groupName.contains("Travel") || groupName.contains("Adventure") -> 
                generateTravelGroupFlow(otherParticipants, currentUserId)
            groupName.contains("Photography") || groupName.contains("Art") -> 
                generateCreativeGroupFlow(otherParticipants, currentUserId)
            else -> generateGenericGroupFlow(otherParticipants, currentUserId)
        }
        
        // Extend the flow to reach messageCount
        val fullGroupFlow = mutableListOf<GroupMessage>()
        fullGroupFlow.addAll(initialGroupFlow)
        
        // Add continuation messages if needed
        while (fullGroupFlow.size < messageCount) {
            val continuationFlow = generateGroupContinuationFlow(
                groupName, 
                otherParticipants, 
                currentUserId,
                conversationIndex,
                fullGroupFlow.size
            )
            fullGroupFlow.addAll(continuationFlow)
        }
        
        // Create messages from the flow
        // Use current time to ensure messages are from "today" for the backend
        val baseTime = System.currentTimeMillis() - (conversationIndex * 60000L) // Only minutes ago, not hours
        
        // For conversations with unread messages, filter the flow to avoid current user messages near the end
        val filteredFlow = if (guaranteedUnreadCount > 0) {
            // Take messages but skip current user messages in the last portion
            val flowToUse = mutableListOf<GroupMessage>()
            var addedCount = 0
            for (msg in fullGroupFlow) {
                // Add all messages from others, but limit current user messages
                if (msg.senderId != currentUserId || addedCount < messageCount - 10) {
                    flowToUse.add(msg)
                    addedCount++
                }
                if (addedCount >= messageCount) break
            }
            flowToUse
        } else {
            fullGroupFlow
        }
        
        for ((index, msgData) in filteredFlow.withIndex()) {
            if (messages.size >= messageCount) break
            
            val messageId = UUID.randomUUID().toString()
            // For conversations with unread messages, ensure all regular flow messages are BEFORE lastViewedAt
            val timestamp = if (guaranteedUnreadCount > 0) {
                conversation.lastViewedAt - ((messageCount - index) * 60000L) // 1 minute per message before lastViewedAt
            } else {
                baseTime - ((messageCount - index) * 60000L) // 1 minute apart, keeps messages within today
            }
            
            messages.add(MessageEntity(
                messageId = messageId,
                conversationId = conversationId,
                senderId = msgData.senderId,
                content = msgData.content,
                timestamp = timestamp,
                messageType = msgData.type,
                isRead = msgData.senderId == currentUserId,
                isDelivered = true,
                replyToMessageId = if (msgData.isReply && messages.isNotEmpty()) {
                    messages[messages.size - 1].messageId
                } else null
            ))
        }
        
        return messages
    }
    
    private fun generateGroupContinuationFlow(
        groupName: String,
        participants: List<ConversationParticipantEntity>,
        currentUserId: String,
        conversationIndex: Int,
        currentMessageCount: Int
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: participants.getOrNull(0)?.userId ?: return emptyList()
        val p3 = participants.getOrNull(2)?.userId ?: participants.getOrNull(0)?.userId ?: return emptyList()
        
        // Create varied continuation patterns based on current message count
        val variation = (conversationIndex + currentMessageCount) % 10
        
        return when (variation) {
            0 -> listOf(
                GroupMessage(p1, "BTW, anyone free tomorrow?"),
                GroupMessage(currentUserId, "What's happening tomorrow?"),
                GroupMessage(p2, "Was thinking we could meet up"),
                GroupMessage(p3, "I'm free after 5"),
                GroupMessage(currentUserId, "Works for me!"),
                GroupMessage(p1, "Perfect, let's do it")
            )
            1 -> listOf(
                GroupMessage(p2, "Just saw something hilarious"),
                GroupMessage(p3, "What? Share!"),
                GroupMessage(p2, "Sending the link"),
                GroupMessage(currentUserId, "😂😂😂"),
                GroupMessage(p1, "I'm dying! This is too good"),
                GroupMessage(p3, "Made my day!")
            )
            2 -> listOf(
                GroupMessage(currentUserId, "Quick question for everyone"),
                GroupMessage(p1, "What's up?"),
                GroupMessage(currentUserId, "Opinion on the new changes?"),
                GroupMessage(p2, "I think they're good"),
                GroupMessage(p3, "Agreed, much better"),
                GroupMessage(p1, "Definitely an improvement")
            )
            3 -> listOf(
                GroupMessage(p3, "Remember what we discussed?"),
                GroupMessage(p1, "About next week?"),
                GroupMessage(p3, "Yeah, that"),
                GroupMessage(currentUserId, "Still on?"),
                GroupMessage(p2, "100%"),
                GroupMessage(p1, "Can't wait!")
            )
            4 -> listOf(
                GroupMessage(p1, "Anyone else tired?"),
                GroupMessage(p2, "Exhausted"),
                GroupMessage(currentUserId, "Same here"),
                GroupMessage(p3, "Long day"),
                GroupMessage(p1, "Need coffee ☕"),
                GroupMessage(p2, "Or sleep 😴")
            )
            5 -> listOf(
                GroupMessage(p2, "Great job today everyone!"),
                GroupMessage(currentUserId, "Thanks! You too!"),
                GroupMessage(p3, "Team effort 💪"),
                GroupMessage(p1, "Couldn't have done it without you all"),
                GroupMessage(currentUserId, "Best team ever"),
                GroupMessage(p2, "Facts!")
            )
            6 -> listOf(
                GroupMessage(currentUserId, "Who's hungry?"),
                GroupMessage(p1, "Always!"),
                GroupMessage(p2, "What are you thinking?"),
                GroupMessage(currentUserId, "Pizza? 🍕"),
                GroupMessage(p3, "Yes please!"),
                GroupMessage(p1, "Order for everyone?")
            )
            7 -> listOf(
                GroupMessage(p3, "Did everyone see the news?"),
                GroupMessage(currentUserId, "Which news?"),
                GroupMessage(p3, "Check the group email"),
                GroupMessage(p1, "Just saw it!"),
                GroupMessage(p2, "Wow, big changes"),
                GroupMessage(currentUserId, "This is huge!")
            )
            8 -> listOf(
                GroupMessage(p1, "Weekend plans?"),
                GroupMessage(p2, "Nothing yet"),
                GroupMessage(currentUserId, "Same"),
                GroupMessage(p3, "We should do something"),
                GroupMessage(p1, "Ideas?"),
                GroupMessage(currentUserId, "I'm open to anything")
            )
            else -> listOf(
                GroupMessage(p2, "How's everyone doing?"),
                GroupMessage(currentUserId, "Good! You?"),
                GroupMessage(p2, "Can't complain"),
                GroupMessage(p1, "All good here"),
                GroupMessage(p3, "Living the dream"),
                GroupMessage(currentUserId, "Love this group ❤️")
            )
        }
    }
    
    private data class GroupMessage(
        val senderId: String,
        val content: String,
        val type: MessageType = MessageType.TEXT,
        val isReply: Boolean = false
    )
    
    private fun generateBestFriendsFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        // Use actual participant IDs, with fallbacks
        val p1 = participants.getOrNull(0)?.userId ?: participants.firstOrNull()?.userId ?: "user_2" // Jordan
        val p2 = participants.getOrNull(1)?.userId ?: participants.getOrNull(0)?.userId ?: "user_3" // Sam  
        val p3 = participants.getOrNull(2)?.userId ?: participants.getOrNull(1)?.userId ?: participants.getOrNull(0)?.userId ?: "user_4" // Alex
        val p4 = participants.getOrNull(3)?.userId ?: participants.getOrNull(2)?.userId ?: participants.getOrNull(1)?.userId ?: participants.getOrNull(0)?.userId ?: "user_5" // Priya
        
        return listOf(
            GroupMessage(currentUserId, "Good morning, besties! ☀️"),
            GroupMessage(p2, "Morning! Still can't believe last night happened 😂"),
            GroupMessage(p4, "I have zero voice left from all that singing!"),
            GroupMessage(p3, "My head is pounding but it was worth it 😅"),
            GroupMessage(p1, "Who has the group selfie from the end of the night?"),
            GroupMessage(currentUserId, "Group selfie", type = MessageType.IMAGE),
            GroupMessage(p2, "Iconic. Alex, your hat is legendary 😂"),
            GroupMessage(p3, "🤡", type = MessageType.STICKER),
            GroupMessage(p4, "We need to print that one!"),
            GroupMessage(p1, "@Sam did you record the dance-off?"),
            GroupMessage(p2, "dance_off.mp4", type = MessageType.FILE),
            GroupMessage(currentUserId, "Nooo, not the evidence! 😂"),
            GroupMessage(p3, "💃"),
            GroupMessage(p4, "We should do a rematch next week!"),
            GroupMessage(p1, "Friday again? Same place?"),
            GroupMessage(p2, "I'm in! But this time, Alex brings snacks."),
            GroupMessage(currentUserId, "Deal! @Alex, you up for it?"),
            GroupMessage(p3, "Challenge accepted. Any requests?"),
            GroupMessage(p4, "Brownies please!"),
            GroupMessage(p1, "karaokebar.com/bookings"),
            GroupMessage(p2, "Booked for 8pm next Friday 🎤"),
            GroupMessage(currentUserId, "🎉", type = MessageType.STICKER),
            GroupMessage(p4, "What songs are we doing this time?"),
            GroupMessage(p3, "No more 'Let It Go' please 😂"),
            GroupMessage(p1, "But that's Priya's signature move!"),
            GroupMessage(p2, "We need a new group anthem."),
            GroupMessage(currentUserId, "I'll make a poll for song choices."),
            GroupMessage(p4, "Spotify playlist: Best Friends Karaoke"),
            GroupMessage(p3, "Adding my picks now!"),
            GroupMessage(p1, "Don't forget 90s classics!"),
            GroupMessage(p2, "🕺"),
            GroupMessage(currentUserId, "Theme night? 90s dress code?"),
            GroupMessage(p4, "Yesss! I have scrunchies ready."),
            GroupMessage(p3, "I'll dig out my old windbreaker."),
            GroupMessage(p1, "Bucket hats for everyone!"),
            GroupMessage(p2, "This is gonna be hilarious."),
            GroupMessage(currentUserId, "90s outfit ready", type = MessageType.IMAGE),
            GroupMessage(p4, "User wins best outfit already 😂"),
            GroupMessage(p3, "🏆", type = MessageType.STICKER),
            GroupMessage(p1, "Can't wait to see you all next week!"),
            GroupMessage(p2, "Best friends forever! 🥳")
        )
    }
    
    private fun generateFriendsGroupFlow(
        groupName: String,
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        val p3 = participants.getOrNull(2)?.userId ?: participants.getOrNull(0)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(currentUserId, "Anyone up for hanging out this weekend? 🎉"),
            GroupMessage(p1, "Yes! I'm so bored"),
            GroupMessage(p2, "Count me in!"),
            GroupMessage(p3, "What are we thinking?"),
            GroupMessage(currentUserId, "Movie night at mine?"),
            GroupMessage(p1, "Perfect! I'll bring popcorn"),
            GroupMessage(p2, "I got the drinks"),
            GroupMessage(p3, "What movie though?"),
            GroupMessage(currentUserId, "Let's vote! Action or comedy?"),
            GroupMessage(p1, "Comedy please 😂"),
            GroupMessage(p2, "Same, need some laughs"),
            GroupMessage(p3, "@${currentUserId} your place at 7?"),
            GroupMessage(currentUserId, "7 works! See you all then"),
            GroupMessage(p1, "Can't wait!"),
            GroupMessage(p2, "Group selfie time!", type = MessageType.IMAGE),
            GroupMessage(p3, "Looking good everyone!"),
            GroupMessage(currentUserId, "Best squad ever ❤️")
        )
    }
    
    private fun generateWorkGroupFlow(
        groupName: String,
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        val p3 = participants.getOrNull(2)?.userId ?: participants.getOrNull(0)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Team, quick update on the project"),
            GroupMessage(p1, "Client loved the proposal! 🎯"),
            GroupMessage(currentUserId, "That's fantastic news!"),
            GroupMessage(p2, "Great work everyone"),
            GroupMessage(p3, "When do we start implementation?"),
            GroupMessage(p1, "Monday morning, 9 AM kickoff"),
            GroupMessage(currentUserId, "I'll prepare the dev environment"),
            GroupMessage(p2, "I'll handle the documentation"),
            GroupMessage(p3, "Design mockups ready", type = MessageType.FILE),
            GroupMessage(p1, "@all Please review before Monday"),
            GroupMessage(currentUserId, "Looks great! Few suggestions..."),
            GroupMessage(p2, "Agree with those changes"),
            GroupMessage(p3, "I'll update by EOD"),
            GroupMessage(p1, "Perfect. Great teamwork! 💪"),
            GroupMessage(currentUserId, "Excited to start this!"),
            GroupMessage(p2, "This is going to be huge")
        )
    }
    
    private fun generateFamilyGroupFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        val p3 = participants.getOrNull(2)?.userId ?: participants.getOrNull(0)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Good morning family! ☀️"),
            GroupMessage(currentUserId, "Morning! How's everyone?"),
            GroupMessage(p2, "Just having breakfast"),
            GroupMessage(p3, "Running late as usual 😅"),
            GroupMessage(p1, "Don't forget dinner on Sunday"),
            GroupMessage(currentUserId, "I'll be there! What should I bring?"),
            GroupMessage(p2, "Your famous dessert?"),
            GroupMessage(p3, "Yes please! The chocolate cake"),
            GroupMessage(p1, "Photo from last Sunday", type = MessageType.IMAGE),
            GroupMessage(currentUserId, "Aww, love this! ❤️"),
            GroupMessage(p2, "We should frame this one"),
            GroupMessage(p3, "Missing grandma in this"),
            GroupMessage(p1, "She'll be there this Sunday!"),
            GroupMessage(currentUserId, "Can't wait to see everyone"),
            GroupMessage(p2, "Family time is the best"),
            GroupMessage(p3, "Love you all! 🤗")
        )
    }
    
    private fun generateStudyGroupFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Anyone started the assignment yet?"),
            GroupMessage(currentUserId, "Just starting now 😬"),
            GroupMessage(p2, "It's due tomorrow!!"),
            GroupMessage(p1, "I know! Let's work together"),
            GroupMessage(currentUserId, "Library in 30?"),
            GroupMessage(p2, "On my way"),
            GroupMessage(p1, "Bringing my notes", type = MessageType.FILE),
            GroupMessage(currentUserId, "You're a lifesaver!"),
            GroupMessage(p2, "Question 3 is killing me"),
            GroupMessage(p1, "Try the formula from lecture 5"),
            GroupMessage(currentUserId, "That worked! Thanks!"),
            GroupMessage(p2, "We got this! 💪")
        )
    }
    
    private fun generateGamingGroupFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        val p3 = participants.getOrNull(2)?.userId ?: participants.getOrNull(0)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Squad up! Who's online? 🎮"),
            GroupMessage(currentUserId, "Loading in now"),
            GroupMessage(p2, "Give me 5 mins"),
            GroupMessage(p3, "Already in lobby"),
            GroupMessage(p1, "What mode today?"),
            GroupMessage(currentUserId, "Ranked?"),
            GroupMessage(p2, "Let's do it!"),
            GroupMessage(p3, "We need better teamwork than last time 😂"),
            GroupMessage(p1, "That was rough lol"),
            GroupMessage(currentUserId, "New strategy: stay together"),
            GroupMessage(p2, "Voice chat?"),
            GroupMessage(p3, "Discord channel 2"),
            GroupMessage(p1, "GG! We crushed it!"),
            GroupMessage(currentUserId, "Best game yet! 🏆"),
            GroupMessage(p2, "One more?"),
            GroupMessage(p3, "Always!")
        )
    }
    
    private fun generateFitnessGroupFlow(
        groupName: String,
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        
        val activity = when {
            groupName.contains("Yoga") -> "yoga"
            groupName.contains("Tennis") -> "tennis"
            groupName.contains("Running") -> "running"
            else -> "workout"
        }
        
        return listOf(
            GroupMessage(p1, "Who's up for $activity tomorrow morning?"),
            GroupMessage(currentUserId, "What time?"),
            GroupMessage(p2, "6 AM too early?"),
            GroupMessage(p1, "Perfect for me"),
            GroupMessage(currentUserId, "I'll be there!"),
            GroupMessage(p2, "Same spot as usual?"),
            GroupMessage(p1, "Yes, don't forget water"),
            GroupMessage(currentUserId, "My muscles are still sore from last time 😅"),
            GroupMessage(p2, "No pain no gain! 💪"),
            GroupMessage(p1, "Today's workout plan", type = MessageType.IMAGE),
            GroupMessage(currentUserId, "Looks intense!"),
            GroupMessage(p2, "We got this!")
        )
    }
    
    private fun generateFoodGroupFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Made something amazing today! 🍝"),
            GroupMessage(p1, "Homemade pasta", type = MessageType.IMAGE),
            GroupMessage(currentUserId, "That looks incredible!"),
            GroupMessage(p2, "Recipe please!!"),
            GroupMessage(p1, "Sending now", type = MessageType.FILE),
            GroupMessage(currentUserId, "Trying this weekend"),
            GroupMessage(p2, "Me too!"),
            GroupMessage(p1, "Let me know how it goes"),
            GroupMessage(currentUserId, "Any tips?"),
            GroupMessage(p1, "Don't overcook the pasta"),
            GroupMessage(p2, "And use fresh herbs!"),
            GroupMessage(currentUserId, "Can't wait to try!")
        )
    }
    
    private fun generateTravelGroupFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Guys! Found cheap flights to Bali! ✈️"),
            GroupMessage(currentUserId, "How cheap?"),
            GroupMessage(p2, "When?"),
            GroupMessage(p1, "$400 round trip, next month"),
            GroupMessage(currentUserId, "I'm interested!"),
            GroupMessage(p2, "Count me in!"),
            GroupMessage(p1, "Booking tonight, who's confirmed?"),
            GroupMessage(currentUserId, "✋ Yes!"),
            GroupMessage(p2, "Me too!"),
            GroupMessage(p1, "This is happening! 🏝️"),
            GroupMessage(currentUserId, "Dream trip!"),
            GroupMessage(p2, "Can't wait!")
        )
    }
    
    private fun generateCreativeGroupFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        val p2 = participants.getOrNull(1)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Today's photo challenge: Golden Hour 📸"),
            GroupMessage(currentUserId, "Perfect timing, just took this", type = MessageType.IMAGE),
            GroupMessage(p2, "Wow! The lighting!"),
            GroupMessage(p1, "That's gorgeous!"),
            GroupMessage(p2, "Here's mine from yesterday", type = MessageType.IMAGE),
            GroupMessage(currentUserId, "Love the composition!"),
            GroupMessage(p1, "We should do a photowalk"),
            GroupMessage(currentUserId, "This weekend?"),
            GroupMessage(p2, "I'm free Saturday"),
            GroupMessage(p1, "Let's meet at the park"),
            GroupMessage(currentUserId, "Can't wait!"),
            GroupMessage(p2, "Bringing my new lens!")
        )
    }
    
    private fun generateGenericGroupFlow(
        participants: List<ConversationParticipantEntity>,
        currentUserId: String
    ): List<GroupMessage> {
        val p1 = participants.getOrNull(0)?.userId ?: return emptyList()
        
        return listOf(
            GroupMessage(p1, "Hey everyone!"),
            GroupMessage(currentUserId, "Hey! What's up?"),
            GroupMessage(p1, "Just checking in"),
            GroupMessage(currentUserId, "All good here"),
            GroupMessage(p1, "Anyone free tonight?"),
            GroupMessage(currentUserId, "What did you have in mind?"),
            GroupMessage(p1, "Maybe grab dinner?"),
            GroupMessage(currentUserId, "Sounds good!"),
            GroupMessage(p1, "7 PM?"),
            GroupMessage(currentUserId, "See you then!")
        )
    }
    
    private fun getContextualMessage(groupName: String?, messageIndex: Int, isShort: Boolean = false): String {
        // Fitness/Sports groups
        if (groupName != null && (groupName.contains("Tennis") || groupName.contains("Gym") || 
            groupName.contains("Yoga") || groupName.contains("Running"))) {
            val fitnessMessages = if (isShort) listOf(
                "I'm in!", "Can't make it", "See you there", "Great workout!", "I'm sore 😅",
                "Let's go!", "What time?", "Count me in", "Crushed it!", "Rest day"
            ) else listOf(
                "Who's joining for the morning session tomorrow?",
                "Just finished my workout, feeling amazing!",
                "Anyone want to try that new class next week?",
                "My legs are still sore from yesterday 😅",
                "Remember to bring your water bottles!",
                "Great session today everyone, same time next week?",
                "I'll be 10 mins late, start warming up without me",
                "New personal record today! Finally hit my goal",
                "The instructor was tough today but it was worth it",
                "Let's sign up for that marathon we talked about"
            )
            return fitnessMessages[messageIndex % fitnessMessages.size]
        }
        
        // Work/Professional groups
        if (groupName != null && (groupName.contains("Team") || groupName.contains("Project") || 
            groupName.contains("Work") || groupName.contains("Marketing") || groupName.contains("Dev"))) {
            val workMessages = if (isShort) listOf(
                "On it", "Will do", "Approved", "Looks good", "Need review",
                "Done", "In progress", "LGTM", "Ship it!", "One sec"
            ) else listOf(
                "Just pushed the latest changes to the repository",
                "The client meeting went well, they're happy with our progress",
                "Can someone review my pull request when you get a chance?",
                "Deadline is next Friday, let's sync up tomorrow",
                "Great work on the presentation everyone!",
                "I'll handle the documentation for this sprint",
                "The deploy is scheduled for 3 PM today",
                "Found a bug in production, working on a fix now",
                "Let's discuss this in tomorrow's standup",
                "Updated the requirements doc with client feedback"
            )
            return workMessages[messageIndex % workMessages.size]
        }
        
        // Study/Education groups
        if (groupName != null && (groupName.contains("Study") || groupName.contains("Exam") || 
            groupName.contains("Class") || groupName.contains("Research"))) {
            val studyMessages = if (isShort) listOf(
                "Got it", "Thanks!", "On page 50", "Done reading", "Need help",
                "Quiz tomorrow!", "Library?", "Study now?", "Chapter 5", "Confused"
            ) else listOf(
                "Did anyone understand the last lecture? I'm a bit lost",
                "Study session at the library tomorrow at 2 PM",
                "Professor posted the assignment, due next Tuesday",
                "Here are my notes from today's class",
                "Can someone explain this concept to me?",
                "Group project meeting after class tomorrow",
                "The exam covers chapters 5 through 8",
                "Found some great practice problems online",
                "Who wants to form a study group for finals?",
                "Just submitted the assignment, hope it went well!"
            )
            return studyMessages[messageIndex % studyMessages.size]
        }
        
        // Food/Coffee groups
        if (groupName != null && (groupName.contains("Coffee") || groupName.contains("Brunch") || 
            groupName.contains("Food") || groupName.contains("Recipe"))) {
            val foodMessages = if (isShort) listOf(
                "Yum!", "Looks delicious", "Recipe?", "I'm hungry", "Let's eat",
                "Coffee time", "So good!", "Want some?", "Craving this", "😋"
            ) else listOf(
                "Just tried that new restaurant downtown, highly recommend!",
                "Who wants to grab coffee tomorrow morning?",
                "Sharing my grandmother's secret recipe with you all",
                "The brunch spot was amazing, we should go back",
                "Anyone know a good place for Thai food?",
                "Made this for dinner tonight, turned out great!",
                "Coffee run in 15 mins, anyone want something?",
                "That farmers market has the best fresh produce",
                "Trying to eat healthier, any recipe suggestions?",
                "Best pizza I've ever had, you have to try this place"
            )
            return foodMessages[messageIndex % foodMessages.size]
        }
        
        // Family groups
        if (groupName != null && (groupName.contains("Family") || groupName.contains("Parents"))) {
            val familyMessages = if (isShort) listOf(
                "Love you", "Miss you", "On my way", "Safe travels", "Call me",
                "❤️", "Thanks Mom", "Hi Dad", "See you soon", "Family dinner?"
            ) else listOf(
                "Looking forward to seeing everyone this weekend!",
                "The kids are doing great, growing so fast",
                "Happy birthday! Hope you have a wonderful day 🎂",
                "Safe travels, let us know when you arrive",
                "Mom's making her famous lasagna for dinner Sunday",
                "Can someone pick up grandma from the airport?",
                "Family photo time! Everyone wear something nice",
                "Missing everyone, can't wait for the holidays",
                "Dad's asking about the TV remote again 😂",
                "Thanks for everything you do for our family"
            )
            return familyMessages[messageIndex % familyMessages.size]
        }
        
        // Gaming groups
        if (groupName != null && groupName.contains("Gaming")) {
            val gamingMessages = if (isShort) listOf(
                "GG!", "One more", "I'm on", "Nice play!", "Carry me",
                "AFK", "Back", "Let's go!", "Ranked?", "Easy win"
            ) else listOf(
                "Anyone up for some games tonight?",
                "Just hit diamond rank! The grind was worth it",
                "New update dropped, checking out the patch notes",
                "That last match was insane, great teamwork!",
                "Need one more for our squad, who's available?",
                "Server maintenance in 30 minutes, heads up",
                "Finally got that achievement I've been grinding for",
                "Anyone want to practice before the tournament?",
                "My internet is lagging, might disconnect",
                "GG everyone, same time tomorrow?"
            )
            return gamingMessages[messageIndex % gamingMessages.size]
        }
        
        // Weekend/Social planning groups
        if (groupName != null && (groupName.contains("Weekend") || groupName.contains("Friends") || 
            groupName.contains("Plans"))) {
            val socialMessages = if (isShort) listOf(
                "I'm free!", "Can't wait", "See you!", "What time?", "Where?",
                "Excited!", "Let's do it", "Count me in", "Maybe next time", "Running late"
            ) else listOf(
                "What's everyone doing this weekend?",
                "Movie night at my place Friday, who's in?",
                "Found this cool event happening downtown Saturday",
                "Beach day tomorrow if the weather's nice!",
                "Anyone want to check out that new bar tonight?",
                "Game night was so fun, let's do it again soon",
                "Happy hour after work? I need a drink 😅",
                "Road trip next month, start planning!",
                "Thanks for last night, had a great time!",
                "Let's grab dinner somewhere nice this week"
            )
            return socialMessages[messageIndex % socialMessages.size]
        }
        
        // Adventure/Travel groups
        if (groupName != null && groupName.contains("Adventure")) {
            val adventureMessages = if (isShort) listOf(
                "Epic!", "So beautiful", "Worth it", "Next stop?", "Let's go!",
                "Packed!", "Ready!", "Adventure time", "Can't wait", "Amazing view"
            ) else listOf(
                "Just booked the flights for our trip!",
                "The hiking trail looks challenging but doable",
                "Don't forget your passport and travel insurance",
                "Weather forecast looks perfect for the weekend",
                "Found an amazing Airbnb near the beach",
                "Who's bringing the camping gear?",
                "The sunset from here is incredible!",
                "Best adventure we've had in years",
                "Already planning our next trip",
                "Thanks for organizing everything!"
            )
            return adventureMessages[messageIndex % adventureMessages.size]
        }
        
        // Default messages for direct chats - more realistic conversation patterns
        val defaultMessages = if (isShort) {
            listOf(
                // Quick responses
                "Hey!", "Yeah", "Sure", "Okay", "Thanks!",
                "No problem", "Sounds good", "On my way", "Got it", "Perfect",
                "Haha", "lol", "😂", "👍", "Cool",
                "Maybe", "Not sure", "Let me check", "One sec", "Yep",
                "Nope", "Can't", "Sorry", "Busy rn", "ttyl",
                
                // Quick questions
                "You there?", "What's up?", "Free?", "Busy?", "Home?",
                "Ready?", "Coming?", "Done?", "You okay?", "Need help?"
            )
        } else {
            // Create conversation threads based on message index for more natural flow
            val conversationThread = messageIndex / 5 // Group messages into conversation threads
            
            val threadMessages = when (conversationThread % 8) {
                0 -> listOf( // Making plans thread
                    "Hey! Are you free this weekend?",
                    "I was thinking we could grab lunch on Saturday",
                    "There's this new Thai place downtown I've been wanting to try",
                    "Cool! What time works for you?",
                    "Perfect, see you at 1pm then!"
                )
                1 -> listOf( // Work/project discussion
                    "Quick question about the project",
                    "Did you finish reviewing the documents I sent?",
                    "Yeah, I had a few comments. Let me send them over",
                    "Thanks! I'll update everything tonight",
                    "Awesome, let's sync up tomorrow morning"
                )
                2 -> listOf( // Sharing news/updates
                    "OMG you'll never guess what just happened!",
                    "I got the promotion!! 🎉",
                    "NO WAY! Congratulations!! That's amazing!",
                    "Thank you! I'm still in shock honestly",
                    "We need to celebrate! Drinks on me Friday?"
                )
                3 -> listOf( // Casual check-in
                    "Hey, how's your day going?",
                    "Pretty good! Just finished that meeting finally",
                    "The one with the difficult client?",
                    "Yep, that one. Went better than expected actually",
                    "That's great! Glad it worked out"
                )
                4 -> listOf( // Help/favor request
                    "Hey, are you around?",
                    "Yeah, what's up?",
                    "My car won't start 😫 Any chance you could give me a jump?",
                    "Of course! Be there in 15 minutes",
                    "You're a lifesaver!! Thank you so much!"
                )
                5 -> listOf( // Sharing media/content
                    "Did you see what happened at the game last night?",
                    "No! I missed it, what happened?",
                    "Check this out...", // Would be followed by image/video
                    "WHAT?! That's insane!",
                    "I know right?! Best game of the season"
                )
                6 -> listOf( // Morning/evening routine
                    "Morning! Coffee run?",
                    "Desperately need caffeine ☕",
                    "Same. Meet you at the usual spot?",
                    "Already walking there lol",
                    "See you in 5!"
                )
                7 -> listOf( // Random/funny conversation
                    "Random question: would you rather fight 100 duck-sized horses or 1 horse-sized duck?",
                    "LMAO where is this coming from??",
                    "Just saw it online and now I can't stop thinking about it 😂",
                    "Definitely the duck-sized horses. A horse-sized duck would be terrifying",
                    "Right?! Those beaks would be massive!"
                )
                else -> listOf(
                    "Hey! How have you been?",
                    "Good! Been super busy with work",
                    "Same here. We should catch up soon",
                    "Definitely! It's been way too long",
                    "Free for coffee next week?"
                )
            }
            
            return threadMessages[messageIndex % threadMessages.size]
        }
        return defaultMessages[messageIndex % defaultMessages.size]
    }
    
    fun generateMessages(
        conversations: List<ConversationEntity>,
        participants: List<ConversationParticipantEntity>,
        messagesPerConversation: Int = 30
    ): List<MessageEntity> {
        val messages = mutableListOf<MessageEntity>()
        val currentUserId = "user_1"
        
        conversations.forEach { conversation ->
            val conversationParticipants = participants.filter { 
                it.conversationId == conversation.conversationId 
            }
            
            // For 1:1 chats, get the other participant
            val otherParticipant = if (!conversation.isGroup) {
                conversationParticipants.firstOrNull { it.userId != currentUserId }
            } else {
                null
            }
            
            // Determine message count and ensure specific unread counts for certain conversations
            val conversationIndex = conversation.conversationId.substringAfter("conv_").toIntOrNull() ?: 0
            val (baseMessageCount, guaranteedUnreadCount) = when (conversationIndex) {
                1 -> Pair(48, 2)  // Conversation 1: ensure 2 unread messages
                2 -> Pair(45, 5)  // Conversation 2: ensure 5 unread messages
                3 -> Pair(35, 15) // Conversation 3: ensure 15 unread messages (group)
                7 -> Pair(49, 1)  // Conversation 7: ensure 1 unread message
                12 -> Pair(47, 3) // Conversation 12: ensure 3 unread messages (group)
                else -> Pair(50, 0) // Regular conversations get 50 messages
            }
            // Total messages = base + unread (to reach 50 total)
            val messageCount = baseMessageCount
            
            var previousMessageId: String? = null
            val conversationMessages = mutableListOf<MessageEntity>()
            
            // Generate conversation flow based on chat type
            if (!conversation.isGroup && otherParticipant != null) {
                // 1:1 chat flow
                val conversationFlow = generateConversationFlow(conversationIndex, messageCount)
                
                for ((index, msgData) in conversationFlow.withIndex()) {
                    if (index >= messageCount) break
                    
                    val messageId = UUID.randomUUID().toString()
                    val i = index + 1
                    
                    // Determine sender based on conversation flow
                    val isFromCurrentUser = msgData.isFromCurrentUser
                    val sender = if (isFromCurrentUser) {
                        conversationParticipants.first { it.userId == currentUserId }
                    } else {
                        otherParticipant
                    }
                    
                    // Use the sender from the conversation flow
                    val finalSender = sender
                    
                    // Calculate timestamp to maintain proper ordering
                    val conversationBaseTime = when {
                        conversationIndex <= 5 -> {
                            // First 5 conversations: today, spaced 30 minutes apart  
                            System.currentTimeMillis() - (conversationIndex * 1800000L) // 30 min apart
                        }
                        conversationIndex <= 10 -> {
                            // Next 5 conversations: yesterday, spaced 2 hours apart
                            System.currentTimeMillis() - 86400000L - ((conversationIndex - 5) * 7200000L)
                        }
                        conversationIndex <= 20 -> {
                            // Next 10: 2-11 days ago
                            System.currentTimeMillis() - ((conversationIndex - 8) * 86400000L)
                        }
                        else -> {
                            // Rest: weeks to months ago
                            System.currentTimeMillis() - ((conversationIndex - 10) * 86400000L * 7) // weeks old
                        }
                    }
                    
                    // For conversations with unread messages, ensure all regular flow messages
                    // are BEFORE lastViewedAt. The unread messages will be added separately.
                    val timestamp = if (guaranteedUnreadCount > 0) {
                        // Place all regular flow messages before lastViewedAt
                        conversation.lastViewedAt - ((messageCount - i + 1) * 300000L) // 5 minutes per message before lastViewedAt
                    } else {
                        // For conversations without unread, use normal timestamps
                        when {
                            i == messageCount -> conversationBaseTime
                            else -> conversationBaseTime - ((messageCount - i) * 300000L) // 5 minutes per message backwards
                        }
                    }
                    
                    // Create message based on conversation flow
                    val message = when (msgData.type) {
                        MessageType.FILE -> {
                            MessageEntity(
                                messageId = messageId,
                                conversationId = conversation.conversationId,
                                senderId = finalSender.userId,
                                content = msgData.content,
                                timestamp = timestamp,
                                messageType = MessageType.FILE,
                                // All messages in regular flow are read (unread messages come separately)
                                isRead = true,
                                isDelivered = true,
                                replyToMessageId = null
                            )
                        }
                        else -> {
                            MessageEntity(
                                messageId = messageId,
                                conversationId = conversation.conversationId,
                                senderId = finalSender.userId,
                                content = msgData.content,
                                timestamp = timestamp,
                                messageType = MessageType.TEXT,
                                // All messages in regular flow are read (unread messages come separately)
                                isRead = true,
                                isDelivered = true,
                                replyToMessageId = if (i > 1 && i % 8 == 0 && previousMessageId != null) {
                                    previousMessageId  // Occasionally reply to previous message
                                } else null
                            )
                        }
                    }
                    
                    previousMessageId = messageId
                    messages.add(message)
                }
                
                // Add additional unread messages from the other participant if needed
                if (guaranteedUnreadCount > 0 && otherParticipant != null) {
                    val unreadMessages = generateUnreadContinuation(
                        conversation,
                        otherParticipant,
                        guaranteedUnreadCount,
                        conversationIndex,
                        previousMessageId
                    )
                    messages.addAll(unreadMessages)
                }
            } else if (conversation.isGroup) {
                // Group chat generation with natural flow
                val groupMessages = generateGroupConversationFlow(
                    conversation.title ?: "Group Chat",
                    conversationParticipants,
                    messageCount,
                    conversationIndex,
                    conversation,
                    guaranteedUnreadCount
                )
                messages.addAll(groupMessages)

                // Add additional unread messages for group chats if needed
                if (guaranteedUnreadCount > 0) {
                    val otherGroupParticipants = conversationParticipants.filter { it.userId != currentUserId }
                    if (otherGroupParticipants.isNotEmpty()) {
                        val unreadMessages = generateGroupUnreadContinuation(
                            conversation,
                            otherGroupParticipants,
                            guaranteedUnreadCount,
                            conversationIndex,
                            groupMessages.lastOrNull()?.messageId
                        )
                        messages.addAll(unreadMessages)
                    }
                }

                // Add SYSTEM messages for 20% of group chats
                // These show up as "Person was added" messages
                if (Random.nextFloat() < 0.2) {
                    val otherGroupParticipants = conversationParticipants.filter { it.userId != currentUserId }
                    if (otherGroupParticipants.isNotEmpty() && groupMessages.isNotEmpty()) {
                        // Add 1-2 system messages at random points in the conversation
                        val systemMessageCount = Random.nextInt(1, 3) // 1 or 2 messages

                        for (sysIdx in 0 until systemMessageCount) {
                            // Pick a random participant to be "added"
                            val addedParticipant = otherGroupParticipants.random()

                            // Find the corresponding user entity to get their name
                            // For now, extract name from userId (format is "user_N")
                            val participantName = addedParticipant.userId
                                .substringAfter("user_")
                                .let { userId ->
                                    // Use the first names list to get a realistic name
                                    val index = userId.toIntOrNull()?.rem(firstNames.size) ?: 0
                                    firstNames[index]
                                }

                            // Insert system message at a random point (not at the very beginning)
                            // Pick a timestamp somewhere in the middle of the conversation
                            val minTimestamp = groupMessages.first().timestamp
                            val maxTimestamp = groupMessages.last().timestamp
                            val timeRange = maxTimestamp - minTimestamp

                            // Place system messages in the middle 70% of the timeline
                            val systemTimestamp = minTimestamp + (timeRange * (0.15 + Random.nextDouble() * 0.7)).toLong()

                            val systemMessage = MessageEntity(
                                messageId = UUID.randomUUID().toString(),
                                conversationId = conversation.conversationId,
                                senderId = currentUserId, // System messages use current user to satisfy foreign key
                                content = "$participantName was added",
                                timestamp = systemTimestamp,
                                messageType = MessageType.SYSTEM,
                                isRead = true,
                                isDelivered = true
                            )

                            messages.add(systemMessage)
                        }
                    }
                }
            } else {
                // Fallback for other cases
                for (i in 1..messageCount) {
                    val messageId = UUID.randomUUID().toString()
                    
                    // For messages that should be unread, ensure they're from other users
                    val sender = if (guaranteedUnreadCount > 0 && i > messageCount - guaranteedUnreadCount) {
                        // These should be unread messages from other users
                        conversationParticipants.firstOrNull { it.userId != currentUserId } 
                            ?: conversationParticipants[i % conversationParticipants.size]
                    } else {
                        conversationParticipants[i % conversationParticipants.size]
                    }
                    
                    // Calculate timestamp to maintain proper ordering
                    val baseTimestamp = conversation.createdAt + (i * 1800000L) // Deterministic: 30 minutes per message
                    
                    // Set timestamps to ensure proper ordering
                    // Calculate the base time for this conversation (deterministic based on index)
                    val conversationBaseTime = when {
                        conversationIndex <= 5 -> {
                            // First 5 conversations: today, spaced 30 minutes apart  
                            System.currentTimeMillis() - (conversationIndex * 1800000L) // 30 min apart
                        }
                        conversationIndex <= 10 -> {
                            // Next 5 conversations: yesterday, spaced 2 hours apart
                            System.currentTimeMillis() - 86400000L - ((conversationIndex - 5) * 7200000L)
                        }
                        conversationIndex <= 20 -> {
                            // Next 10: 2-11 days ago
                            System.currentTimeMillis() - ((conversationIndex - 8) * 86400000L)
                        }
                        else -> {
                            // Rest: weeks to months ago
                            System.currentTimeMillis() - ((conversationIndex - 10) * 86400000L * 7) // weeks old
                        }
                    }
                    
                    val timestamp = when {
                        // Last message gets the conversation's designated time
                        i == messageCount -> conversationBaseTime
                        // Unread messages for conversations with guaranteed unread should be recent
                        (conversationIndex == 1 || conversationIndex == 2 || conversationIndex == 3 || 
                         conversationIndex == 7 || conversationIndex == 12) 
                            && i > messageCount - guaranteedUnreadCount -> {
                            // Place unread messages just before the last message
                            val unreadIndex = i - (messageCount - guaranteedUnreadCount)
                            conversationBaseTime - (messageCount - i) * 60000L // 1 minute before last message
                        }
                        // All other messages: progressively older
                        else -> conversationBaseTime - ((messageCount - i) * 3600000L) // 1 hour per message backwards
                    }
                    
                    // Determine message type deterministically based on message index
                    val messageType = when (i % 10) {
                        0 -> MessageType.IMAGE
                        1 -> MessageType.VIDEO
                        2 -> MessageType.STICKER
                        3 -> MessageType.GIF
                        9 -> MessageType.VOICE_NOTE
                        else -> MessageType.TEXT // Most messages are text
                    }
                    
                    val message = when (messageType) {
                        MessageType.TEXT -> {
                            // Use contextual message based on group type
                            val isShortMessage = i % 10 in listOf(0, 1, 4, 7)
                            var messageContent = getContextualMessage(
                                conversation.title,
                                i,
                                isShortMessage
                            )
                            
                            // Add mentions for group chats
                            messageContent = addMentionIfGroup(
                                messageContent,
                                conversation.isGroup,
                                conversationParticipants
                            )
                            
                            MessageEntity(
                                messageId = messageId,
                                conversationId = conversation.conversationId,
                                senderId = sender.userId,
                                content = messageContent,
                                timestamp = timestamp,
                                messageType = MessageType.TEXT,
                                isRead = sender.userId == currentUserId, // Only unread if from another user
                                isDelivered = true,
                                replyToMessageId = if (i % 7 == 0 && previousMessageId != null) {
                                    previousMessageId
                                } else null
                            )
                        }
                        
                        MessageType.IMAGE -> {
                        val description = imageDescriptions[i % imageDescriptions.size]
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = description,
                            timestamp = timestamp,
                            messageType = MessageType.IMAGE,
                                                    mediaUrl = null, // Use local placeholder instead of external URL
                        thumbnailUrl = null, // Use local placeholder instead of external URL
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.VIDEO -> {
                        val description = videoDescriptions[i % videoDescriptions.size]
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = description,
                            timestamp = timestamp,
                            messageType = MessageType.VIDEO,
                                                    mediaUrl = null, // Use local placeholder instead of external URL
                        thumbnailUrl = null, // Use local placeholder instead of external URL
                            duration = 30 + (i * 10), // Deterministic duration
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.LINK -> {
                        val title = linkTitles[i % linkTitles.size]
                        val description = linkDescriptions[i % linkDescriptions.size]
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "Check this out: $title",
                            timestamp = timestamp,
                            messageType = MessageType.LINK,
                            linkUrl = null, // Use local placeholder instead of external URL
                            linkTitle = title,
                            linkDescription = description,
                            linkImageUrl = null, // Use local placeholder instead of external URL
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.FILE -> {
                        val fileName = fileNames[i % fileNames.size]
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "Shared a file: $fileName",
                            timestamp = timestamp,
                            messageType = MessageType.FILE,
                            mediaUrl = null, // Use local placeholder instead of external URL
                            fileName = fileName,
                            fileSize = 1024L * (i + 1), // Deterministic file size
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.GIF -> {
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "GIF",
                            timestamp = timestamp,
                            messageType = MessageType.GIF,
                                                    mediaUrl = null, // Use local placeholder instead of external URL
                        thumbnailUrl = null, // Use local placeholder instead of external URL
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.VOICE_NOTE -> {
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "Voice message",
                            timestamp = timestamp,
                            messageType = MessageType.VOICE_NOTE,
                            mediaUrl = null, // Use local placeholder instead of external URL
                            duration = 5 + (i % 115), // Deterministic duration
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.LOCATION -> {
                        val locations = listOf(
                            "New York, NY", "Los Angeles, CA", "Chicago, IL",
                            "Houston, TX", "Phoenix, AZ", "Philadelphia, PA",
                            "San Antonio, TX", "San Diego, CA", "Dallas, TX",
                            "San Jose, CA", "Austin, TX", "Jacksonville, FL"
                        )
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "📍 ${locations[i % locations.size]}",
                            timestamp = timestamp,
                            messageType = MessageType.LOCATION,
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.STICKER -> {
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "Sticker",
                            timestamp = timestamp,
                            messageType = MessageType.STICKER,
                            mediaUrl = null, // Use local placeholder instead of external URL
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                    
                    MessageType.SYSTEM -> {
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "System message",
                            timestamp = timestamp,
                            messageType = MessageType.SYSTEM,
                            isRead = true, // System messages are always read
                            isDelivered = true
                        )
                    }
                    
                    MessageType.AUDIO -> {
                        MessageEntity(
                            messageId = messageId,
                            conversationId = conversation.conversationId,
                            senderId = sender.userId,
                            content = "Audio file",
                            timestamp = timestamp,
                            messageType = MessageType.AUDIO,
                            mediaUrl = null, // Use local placeholder instead of external URL
                            duration = 30 + (i % 270), // Deterministic duration
                            isRead = sender.userId == currentUserId, // Only unread if from another user
                            isDelivered = true
                        )
                    }
                }
                
                    messages.add(message)
                    previousMessageId = messageId
                }
            }
        }
        
        return messages.sortedBy { it.timestamp }
    }
    
    private fun generateGroupName(index: Int): String {
        // Much larger variety of group names with their contextually appropriate emojis
        val groupNamesWithEmojis = listOf(
            // Work/Professional
            "Project Team" to listOf("", "💼", "📊", "🎯"),
            "Work Colleagues" to listOf("", "💻", "🏢", "👔"),
            "Dev Team" to listOf("", "💻", "⚡", "🚀"),
            "Marketing Squad" to listOf("", "📈", "💡", "🎯"),
            "Sales Force" to listOf("", "💰", "📊", "🤝"),
            "Design Studio" to listOf("", "🎨", "✏️", "🖌️"),
            "Leadership Team" to listOf("", "👥", "🌟", "💼"),
            "Remote Workers" to listOf("", "🏠", "💻", "🌍"),
            
            // Education/Learning
            "Study Group" to listOf("", "📚", "✏️", "🎓"),
            "Research Team" to listOf("", "🔬", "📊", "🔍"),
            "Class of 2024" to listOf("", "🎓", "📚", "🏫"),
            "Homework Help" to listOf("", "📝", "💡", "📖"),
            "Exam Prep" to listOf("", "📚", "⏰", "✏️"),
            "Language Exchange" to listOf("", "🌐", "💬", "🗣️"),
            "Writing Workshop" to listOf("", "✍️", "📝", "📖"),
            
            // Social/Fun
            "Weekend Plans" to listOf("", "🎉", "🌟", "🎊"),
            "Party Planning" to listOf("", "🎈", "🎊", "🥳"),
            "Happy Hour" to listOf("", "🍻", "🍷", "🥂"),
            "Brunch Crew" to listOf("", "🥞", "☕", "🍳"),
            "Night Owls" to listOf("", "🌙", "🦉", "⭐"),
            "Early Birds" to listOf("", "🌅", "☀️", "🐦"),
            "Adventure Time" to listOf("", "🗺️", "🎒", "⛰️"),
            
            // Family/Friends
            "Family Chat" to listOf("", "❤️", "👨‍👩‍👧‍👦", "🏠"),
            "Best Friends" to listOf("", "💕", "🤗", "✨"),
            "Old Friends" to listOf("", "💫", "🕰️", "📸"),
            "Roommates" to listOf("", "🏠", "🔑", "🛋️"),
            "Neighbors" to listOf("", "🏘️", "👋", "🏡"),
            "Extended Family" to listOf("", "👪", "❤️", "🌳"),
            "Siblings" to listOf("", "👫", "💕", "🏠"),
            
            // Hobbies/Interests
            "Book Club" to listOf("", "📖", "📚", "🔖"),
            "Gaming Squad" to listOf("", "🎮", "🕹️", "👾"),
            "Movie Buffs" to listOf("", "🎬", "🍿", "📽️"),
            "Music Lovers" to listOf("", "🎵", "🎸", "🎤"),
            "Photography Club" to listOf("", "📸", "📷", "🖼️"),
            "Art Collective" to listOf("", "🎨", "🖌️", "🖼️"),
            "Cooking Club" to listOf("", "👨‍🍳", "🍳", "📖"),
            "Garden Society" to listOf("", "🌱", "🌻", "🌿"),
            
            // Sports/Fitness
            "Fitness Group" to listOf("", "💪", "🏃", "🏋️"),
            "Running Club" to listOf("", "🏃", "👟", "🏅"),
            "Yoga Class" to listOf("", "🧘", "🕉️", "✨"),
            "Soccer Team" to listOf("", "⚽", "🥅", "🏆"),
            "Basketball Squad" to listOf("", "🏀", "🏆", "👟"),
            "Tennis Partners" to listOf("", "🎾", "🏆", "🎯"),
            "Cycling Group" to listOf("", "🚴", "🚵", "🏔️"),
            "Gym Buddies" to listOf("", "💪", "🏋️", "💯"),
            
            // Food/Drink
            "Coffee Lovers" to listOf("", "☕", "☕", "☕"),
            "Foodies Unite" to listOf("", "🍕", "🍔", "🥘"),
            "Wine Club" to listOf("", "🍷", "🍇", "🥂"),
            "Recipe Exchange" to listOf("", "👨‍🍳", "📖", "🥘"),
            "Lunch Group" to listOf("", "🥗", "🍱", "🍽️"),
            "Dinner Club" to listOf("", "🍽️", "🍝", "🥂"),
            
            // Travel/Location
            "Travel Buddies" to listOf("", "✈️", "🌍", "🗺️"),
            "Road Trip Crew" to listOf("", "🚗", "🛣️", "🗺️"),
            "Beach House" to listOf("", "🏖️", "🌊", "☀️"),
            "City Explorers" to listOf("", "🏙️", "🗺️", "📸"),
            "Mountain Cabin" to listOf("", "🏔️", "🏕️", "🔥"),
            
            // Tech/Professional
            "Tech Talk" to listOf("", "💻", "🚀", "⚡"),
            "Startup Founders" to listOf("", "🚀", "💡", "📈"),
            "Crypto Chat" to listOf("", "₿", "📊", "🚀"),
            "AI Enthusiasts" to listOf("", "🤖", "🧠", "💡"),
            "Web Developers" to listOf("", "🌐", "💻", "⚡"),
            
            // Community/Organizations
            "Neighborhood Watch" to listOf("", "🏘️", "👀", "🚨"),
            "Alumni Network" to listOf("", "🎓", "🏫", "📜"),
            "Volunteer Group" to listOf("", "🤝", "❤️", "🌟"),
            "Parent Group" to listOf("", "👶", "👪", "🏫"),
            "Event Planning" to listOf("", "📅", "🎪", "✨"),
            
            // Creative/Fun Names
            "The Squad" to listOf("", "💫", "🔥", "💯"),
            "Dream Team" to listOf("", "⭐", "🌟", "✨"),
            "Cool Kids" to listOf("", "😎", "🆒", "⭐"),
            "The Crew" to listOf("", "🚀", "💪", "🔥"),
            "Inner Circle" to listOf("", "⭕", "💫", "🔐"),
            "The Gang" to listOf("", "🤘", "🎯", "💥"),
            "Brainstormers" to listOf("", "🧠", "💡", "⚡"),
            "Think Tank" to listOf("", "🧠", "💭", "💡")
        )
        
        val selectedGroup = groupNamesWithEmojis[index % groupNamesWithEmojis.size]
        val groupName = selectedGroup.first
        val possibleEmojis = selectedGroup.second
        
        // Deterministic emoji selection based on index
        val emoji = if (index % 2 == 0 && possibleEmojis.isNotEmpty()) {
            val selectedEmoji = possibleEmojis[index % possibleEmojis.size]
            if (selectedEmoji.isNotEmpty()) " $selectedEmoji" else ""
        } else {
            ""
        }
        
        return groupName + emoji
    }
    
    suspend fun updateConversationsWithLastMessage(
        conversations: List<ConversationEntity>,
        messages: List<MessageEntity>,
        currentUserId: String = "user_1"
    ): List<ConversationEntity> {
        println("=== UPDATING CONVERSATIONS WITH LAST MESSAGE ===")
        return conversations.map { conversation ->
            val conversationMessages = messages.filter { it.conversationId == conversation.conversationId }
                .sortedBy { it.timestamp }
            val lastMessage = conversationMessages.maxByOrNull { it.timestamp }
            
            // Count incoming messages that arrived after lastViewedAt
            val unreadCount = conversationMessages.count { message ->
                message.senderId != currentUserId && 
                message.timestamp > conversation.lastViewedAt
            }
            
            // Log conversation details
            val convIndex = conversation.conversationId.substringAfter("conv_").toIntOrNull() ?: 0
            if (convIndex <= 10) {
                println("Conv $convIndex: lastMessage timestamp = ${lastMessage?.timestamp}, " +
                        "formatted = ${if (lastMessage != null) java.time.Instant.ofEpochMilli(lastMessage.timestamp) else "null"}, " +
                        "unreadCount = $unreadCount")
            }
            
            if (lastMessage != null) {
                conversation.copy(
                    lastMessageId = lastMessage.messageId,
                    lastMessageText = when (lastMessage.messageType) {
                        MessageType.IMAGE -> "📷 Photo"
                        MessageType.VIDEO -> "📹 Video"
                        MessageType.AUDIO -> "🎵 Audio"
                        MessageType.FILE -> "📎 ${lastMessage.fileName ?: "File"}"
                        MessageType.LOCATION -> lastMessage.content
                        MessageType.STICKER -> "Sticker"
                        MessageType.GIF -> "GIF"
                        MessageType.VOICE_NOTE -> "🎤 Voice message"
                        MessageType.LINK -> "🔗 ${lastMessage.linkTitle ?: "Link"}"
                        MessageType.SYSTEM -> lastMessage.content
                        MessageType.TEXT -> lastMessage.content
                    },
                    lastMessageTimestamp = lastMessage.timestamp,
                    updatedAt = lastMessage.timestamp,
                    unreadCount = unreadCount // Set the actual unread count
                )
            } else {
                conversation.copy(
                    unreadCount = unreadCount // Even if no messages, update unread count to 0
                )
            }
        }
    }

    // Generate avatar URL for individual users
    fun generateIndividualAvatarUrl(userId: String, conversationId: String): String {
        // Extract index from conversationId for consistency
        val convIndex = conversationId.removePrefix("conv_").toIntOrNull() ?: 1
        
        // Use conversation index to select avatar
        val index = convIndex % avatarUrls.size
        return avatarUrls[index]
    }
    
    
    // Generate avatar URL for groups based on group name with uniqueness
    fun generateGroupAvatarUrl(groupName: String, conversationIndex: Int = 0): String {
        val lowerGroupName = groupName.lowercase()
        
        // First, try to match specific themes
        val themeAvatar = when {
            lowerGroupName.contains("family") -> groupAvatars["Family"]
            lowerGroupName.contains("work") || lowerGroupName.contains("office") -> groupAvatars["Work"]
            lowerGroupName.contains("friend") -> groupAvatars["Friends"]
            lowerGroupName.contains("school") -> groupAvatars["School"]
            lowerGroupName.contains("college") -> groupAvatars["College"]
            lowerGroupName.contains("university") -> groupAvatars["University"]
            lowerGroupName.contains("research") || lowerGroupName.contains("lab") || lowerGroupName.contains("science") -> groupAvatars["Research"]
            lowerGroupName.contains("team") && !lowerGroupName.contains("research") -> groupAvatars["Team"]
            lowerGroupName.contains("project") -> groupAvatars["Project"]
            lowerGroupName.contains("exam") || lowerGroupName.contains("test") || lowerGroupName.contains("quiz") -> groupAvatars["Exam"]
            lowerGroupName.contains("study") || lowerGroupName.contains("class") || lowerGroupName.contains("homework") -> groupAvatars["Study"]
            lowerGroupName.contains("game") -> groupAvatars["Gaming"]
            lowerGroupName.contains("sport") -> groupAvatars["Sports"]
            lowerGroupName.contains("music") -> groupAvatars["Music"]
            lowerGroupName.contains("travel") -> groupAvatars["Travel"]
            lowerGroupName.contains("food") || lowerGroupName.contains("cook") -> groupAvatars["Food"]
            lowerGroupName.contains("fit") || lowerGroupName.contains("gym") -> groupAvatars["Fitness"]
            else -> null
        }
        
        // If we have a theme match, return it
        if (themeAvatar != null) {
            return themeAvatar
        }
        
        // For groups without specific themes, rotate through individual avatars for variety
        // This ensures each group gets a unique avatar even if no theme matches
        val avatarIndex = (groupName.hashCode().absoluteValue + conversationIndex) % avatarUrls.size
        return avatarUrls[avatarIndex]
    }

}

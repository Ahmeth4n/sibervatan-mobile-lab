cmake_minimum_required(VERSION 3.4.1)

# Mevcut kütüphaneler
add_library(port_checker SHARED
        port_checker.cpp)

add_library(frida_checker SHARED
        frida_checker.cpp)

add_library(frida_process_checker SHARED
        frida_process_checker.cpp)

add_library(frida_library_checker SHARED
        frida_library_checker.cpp)

# Yeni root_checker kütüphanesi
add_library(root_checker SHARED
        root_checker.cpp)

find_library(log-lib log)

# Kütüphaneleri linkleyin
target_link_libraries(port_checker ${log-lib})
target_link_libraries(frida_checker ${log-lib})
target_link_libraries(frida_process_checker ${log-lib})
target_link_libraries(frida_library_checker ${log-lib})
target_link_libraries(root_checker ${log-lib})

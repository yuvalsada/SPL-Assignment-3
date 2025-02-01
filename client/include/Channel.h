#pragma once

#include <string>
using std::string;
#include <iostream>
#include <unordered_map>
using std::unordered_map;
#include "event.h"
#include <vector>
using std::vector;

class Channel {
    
private:
    std::string name;
    std::unordered_map<string, Event> userUpdates;

public:
    Channel(string name);
    virtual ~Channel();
    void summary(string user, string fileName);
    string getName();
    void addChannelEvent(string name, Event *event);
    string Channel::epochToDateTime(time_t epoch);
};
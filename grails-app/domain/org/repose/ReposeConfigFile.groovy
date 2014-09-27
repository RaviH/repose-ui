package org.repose

import org.bson.types.ObjectId

class ReposeConfigFile {
    ObjectId id
    String fileContent
    int version
    static belongsTo = [ ReposeConfig ]

    static constraints = {
    }
}

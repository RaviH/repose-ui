package org.repose

/**
 * Created by ravi on 9/15/14.
 */
def someReallyLongLine = """
I lovef ekgfniegfniefniengiengienbgfiqenfiuengfiuwebruigfrfe2rfe2rfefeff3rff3re1f3r
I lovef ekgfniegfniefniengiengienbgfiqenfiuengfiuwebruigfrfe2rfe2rfefeff3rff3re1f3r
I lovef ekgfniegfniefniengiengienbgfiqenfiuengfiuwebruigfrfe2rfe2rfefeff3rff3re1f3r
I lovef ekgfniegfniefniengiengienbgfiqenfiuengfiuwebruigfrfe2rfe2rfefeff3rff3re1f3r
I lovef ekgfniegfniefniengiengienbgfiqenfiuengfiuwebruigfrfe2rfe2rfefeff3rff3re1f3r
I lovef ekgfniegfniefniengiengienbgfiqenfiuengfiuwebruigfrfe2rfe2rfefeff3rff3re1f3r
"""
def file = new File("/home/ravi/deleteMe.txt")
int i = 0
while(true) {
    i++
    file << "$i\n"
    Thread.sleep(2000)
}
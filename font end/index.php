<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.0/css/bootstrap.min.css" integrity="sha384-SI27wrMjH3ZZ89r4o+fGIJtnzkAnFs3E4qz9DIYioCQ5l9Rd/7UAa8DHcaL8jkWt" crossorigin="anonymous">
    <script src="https://kit.fontawesome.com/9cdefafb29.js" crossorigin="anonymous"></script>
</head>
<body>
    <?php
        if(isset($_POST['start'])){
            $startbase = $_POST['start'];
            $stopbase = $_POST['stop'];
            $start = urlencode($startbase);
            $stop = urlencode($stopbase);
        }

        $url = "http://34.87.20.177:8080/viewstation";
        $response = file_get_contents($url);
        $result = json_decode($response);
    ?>
    <div class="container p-3 mt-4">
        <h1 class="text-center my-4">Train Fare Service</h1>
    
    <form action="" method="POST">
        <div class="row">
            <div class="col-md-4">
                <select class="form-control" name="start">
                    <option disabled selected>สถานีต้นทาง</option>
                    <?php
                        echo '<option disabled >---Airport Link---</option>';
                        foreach($result as $station){
                            foreach($station->Airlink as $airlink){
                                echo "<option value='" . $airlink . "'>" . $airlink . "</option>";
                            }
                        }
                        echo '<option disabled >---BTS---</option>';
                        foreach($result as $station){
                            foreach($station->BTS as $bts){
                                echo "<option value='" . $bts . "'>" . $bts . "</option>";
                            }
                        }
                        echo '<option disabled >---MRT---</option>';
                        foreach($result as $station){
                            foreach($station->MRT as $mrt){
                                echo "<option value='" . $mrt . "'>" . $mrt . "</option>";
                            }
                        }
                    ?>
                </select>
            </div>
            <div class="col-md-4">
                <select class="form-control" name="stop">
                <option disabled selected>สถานีปลายทาง</option>
                    <?php
                        echo '<option disabled >---Airport Link---</option>';
                        foreach($result as $station){
                            foreach($station->Airlink as $airlink){
                                echo "<option value='" . $airlink . "'>" . $airlink . "</option>";
                            }
                        }
                        echo '<option disabled >---BTS---</option>';
                        foreach($result as $station){
                            foreach($station->BTS as $bts){
                                echo "<option value='" . $bts . "'>" . $bts . "</option>";
                            }
                        }
                        echo '<option disabled >---MRT---</option>';
                        foreach($result as $station){
                            foreach($station->MRT as $mrt){
                                echo "<option value='" . $mrt . "'>" . $mrt . "</option>";
                            }
                        }
                    ?>
                </select>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-outline-dark w-100" name="all">ค้นหาทั้งหมด</button>
            </div>
            <div class="col-md-2">
                <button type="submit" class="btn btn-outline-secondary w-100" name="recom">ค้นหาที่แนะนำ</button>
            </div>
        </div>
    </form>

    <?php
    if(isset($_POST['recom'])){
        $lowfare_link = "http://34.87.20.177:8080/$start/to/$stop/lowestfare";
        $lowfare_data = file_get_contents($lowfare_link);
        $lowfare = json_decode($lowfare_data);

        echo "<h3 class='my-4 text-center'>เดินทางจาก " . $startbase . " ไปยัง " . $stopbase . "</h3>";
        
        echo "<div class='container p-3 border my-3'>";
        echo '<span class="badge badge-pill badge-danger">ประหยัดสุด</span>&nbsp;';
        echo '<span class="badge badge-pill badge-warning"><i class="fas fa-dollar-sign"></i> ' . $lowfare->fare . '</span>&nbsp;';
        echo '<span class="badge badge-pill badge-secondary"><i class="far fa-clock"></i> ' . $lowfare->time . ' min</span>&nbsp;';

        if(isset($lowfare->route)){
            echo '<span class="badge badge-pill badge-dark"><i class="fas fa-car"></i> ' . $lowfare->route . '</span>&nbsp;';
        }
        else{
            echo '<span class="badge badge-pill badge-dark"><i class="fas fa-car"></i> เดินทางภายในสายเดียวกัน</span>&nbsp;';
        }
        echo "</div>";

        $lowtime_link = "http://34.87.20.177:8080/$start/to/$stop/lowesttime";
        $lowtime_data = file_get_contents($lowtime_link);
        $lowtime = json_decode($lowtime_data);

        echo "<div class='container p-3 border my-3'>";
        echo '<span class="badge badge-pill badge-success">ถึงไวสุด</span>&nbsp;';
        echo '<span class="badge badge-pill badge-warning"><i class="fas fa-dollar-sign"></i> ' . $lowtime->fare . '</span>&nbsp;';
        echo '<span class="badge badge-pill badge-secondary"><i class="far fa-clock"></i> ' . $lowtime->time . ' min</span>&nbsp;';

        if(isset($lowtime->route)){
            echo '<span class="badge badge-pill badge-dark"><i class="fas fa-car"></i> ' . $lowtime->route . '</span>&nbsp;';
        }
        else{
            echo '<span class="badge badge-pill badge-dark"><i class="fas fa-car"></i> เดินทางภายในสายเดียวกัน</span>&nbsp;';
        }
        echo "</div>";
        
    }

    if(isset($_POST['all'])){
        $link = "http://34.87.20.177:8080/$start/to/$stop/";
        $data = file_get_contents($link);
        $all = json_decode($data);

        echo "<h3 class='my-4 text-center'>เดินทางจาก " . $startbase . " ไปยัง " . $stopbase . "</h3>";

        for($i = 0; $i < $all->possibleRoutes; $i++){
        
            echo "<div class='container p-3 border my-3'>";
            echo '<span class="badge badge-pill badge-warning"><i class="fas fa-dollar-sign"></i> ' . $all->routes[$i]->fare . '</span>&nbsp;';
            echo '<span class="badge badge-pill badge-secondary"><i class="far fa-clock"></i> ' . $all->routes[$i]->time . ' min</span>&nbsp;';

            if(isset($all->routes[$i]->route)){
                echo '<span class="badge badge-pill badge-dark"><i class="fas fa-car"></i> ' . $all->routes[$i]->route . '</span>&nbsp;';
            }
            else{
                echo '<span class="badge badge-pill badge-dark"><i class="fas fa-car"></i> เดินทางภายในสายเดียวกัน</span>&nbsp;';
            }
            echo "</div>";
        }
        
    }
    ?>

    </div>
</body>
</html>
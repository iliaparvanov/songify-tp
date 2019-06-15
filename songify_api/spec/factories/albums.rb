FactoryBot.define do
    factory :album do
        title { Faker::Lorem.word }
        artist_id nil
    end
  end